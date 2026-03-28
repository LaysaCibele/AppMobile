package com.example.appmobile.repository


import com.example.appmobile.model.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class AuthRepository {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    fun cadastrar(usuario: Usuario, callback: (Boolean, String?) -> Unit) {
        auth.createUserWithEmailAndPassword(usuario.email, usuario.senha)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val uid = auth.currentUser?.uid ?: ""

                    // 2. Se criar o login, salvamos o nome no Firestore usando o UID
                    val dadosUsuario = mapOf(
                        "uid" to uid,
                        "nome" to usuario.nome,
                        "email" to usuario.email
                    )

                    db.collection("usuarios").document(uid)
                        .set(dadosUsuario)
                        .addOnCompleteListener { dbTask ->
                            if (dbTask.isSuccessful) {
                                callback(true, null)
                            } else {
                                callback(false, "Erro ao salvar dados no banco.")
                            }
                        }
                } else {
                    callback(false, task.exception?.message) // Caso o email ja exista, retorna erro
                }
            }
    }

    fun logar(email: String, senha: String, callback: (Boolean, String?) -> Unit) {
        auth.signInWithEmailAndPassword(email, senha)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true, null)
                } else {
                    callback(false, "Email ou senha incorretos.")
                }
            }
    }

    // VERIFICAR SE JÁ ESTÁ LOGADO (Para não pedir login toda vez e tals )
    fun estaLogado(): Boolean = auth.currentUser != null
}
package com.example.appmobile.repository

import com.example.appmobile.model.Jogo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class GameRepository {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    fun salvarJogoNaBiblioteca(jogo: Jogo, callback: (Boolean) -> Unit) {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            val jogoComDono = jogo.copy(idUsuario = userId)

            db.collection("usuarios").document(userId)
                .collection("meus_jogos")
                .add(jogoComDono)
                .addOnSuccessListener { callback(true) }
                .addOnFailureListener { callback(false) }
        } else {
            callback(false)
        }
    }

    // status: 0 = Quero Jogar, 1 = Jogando, 2 = Zerado
    fun buscarJogosPorStatus(status: Int, callback: (List<Jogo>) -> Unit) {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            db.collection("usuarios").document(userId)
                .collection("meus_jogos")
                .whereEqualTo("status", status) // Aq que coloquei o filtro das abas
                .orderBy("dataAdicionado", Query.Direction.DESCENDING)
                .addSnapshotListener { snapshot, error ->
                    if (error != null || snapshot == null) {
                        callback(emptyList())
                        return@addSnapshotListener
                    }
                    val lista = snapshot.toObjects(Jogo::class.java)
                    callback(lista)
                }
        }
    }

    fun atualizarJogo(jogoId: String, novosDados: Map<String, Any>, callback: (Boolean) -> Unit){
        val userId = auth.currentUser?.uid
        if (userId != null) {
            db.collection("usuarios").document(userId)
                .collection("meus_jogos").document(jogoId)
                .update(novosDados)
                .addOnSuccessListener { callback(true) }
                .addOnFailureListener { callback(false) }
        }
    }
}
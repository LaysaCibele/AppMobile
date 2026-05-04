package com.example.appmobile.repository

import android.util.Log
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
                .whereEqualTo("status", status)
                // REMOVEMOS O ORDER BY PARA NÃO PRECISAR DE ÍNDICE
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        Log.d("DEBUG_APP", "Erro no Firebase: ${error.message}")
                        callback(emptyList())
                        return@addSnapshotListener
                    }
                    if (snapshot != null) {
                        val lista = snapshot.toObjects(Jogo::class.java)
                        Log.d("DEBUG_APP", "Busca status $status retornou: ${lista.size} itens")
                        callback(lista)
                    }
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

    fun criarJogoDeTeste(callback: (Boolean) -> Unit) {
        val userId = auth.currentUser?.uid ?: return

        val jogoTeste = Jogo(
            id = "id_fixo_teste", // ID para o Firebase
            idUsuario = userId,
            nome = "Stardew Valley",
            descricao = "Um jogo de fazenda muito legal!",
            genero = "RPG",
            capaImagem = "https://i.pinimg.com/736x/b3/bb/90/b3bb90f1e80d335cf72e945222ebf347.jpg",
            linkWiki = "https://stardewvalleywiki.com/",
            mapa = "https://stardew.app/",
            status = 1,  //Vai aparecer em jogando
            dataAdicionado = System.currentTimeMillis()
        )

        db.collection("usuarios").document(userId)
            .collection("meus_jogos").document(jogoTeste.id)
            .set(jogoTeste)
            .addOnSuccessListener { callback(true) }
            .addOnFailureListener { callback(false) }
    }
}
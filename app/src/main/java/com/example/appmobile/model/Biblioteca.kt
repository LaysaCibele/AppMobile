package com.example.appmobile.model

data class Biblioteca(
    val id: String = "",
    val usuarioId: String = "",
    val jogoId: String = "",
    val anotacoes: String = "",
    val status: String = "QUERO_JOGAR",
    val timestamp: Long = System.currentTimeMillis() 
)
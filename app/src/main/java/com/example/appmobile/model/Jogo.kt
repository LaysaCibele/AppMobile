package com.example.appmobile.model

import java.io.Serializable

data class Jogo(
    val id: String = "",
    val idUsuario: String = "",
    val nome: String = "",
    val descricao: String = "",
    val genero: String= "",
    val plataformas: String= "",
    val capaImagem: String = "",
    val linkWiki: String = "",

    var status: Int = 0,
    var anotacoes: String = "",
    var dataAdicionado: Long = System.currentTimeMillis()
) : Serializable
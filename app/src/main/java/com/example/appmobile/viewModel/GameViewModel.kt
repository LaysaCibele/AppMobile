package com.example.appmobile.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appmobile.model.Jogo
import com.example.appmobile.repository.GameRepository

class GameViewModel : ViewModel() {

    private val repository = GameRepository()

    val jogosQueroJogar = MutableLiveData<List<Jogo>>()
    val jogosJogando = MutableLiveData<List<Jogo>>()
    val jogosZerados = MutableLiveData<List<Jogo>>()

  //Essa função carrega os jogos de uma das abas específicas
    fun carregarBiblioteca(status: Int) {
        repository.buscarJogosPorStatus(status) { lista ->
            when (status) {
                0 -> jogosQueroJogar.postValue(lista)
                1 -> jogosJogando.postValue(lista)
                2 -> jogosZerados.postValue(lista)
            }
        }
    }

    fun mudarStatusJogo(jogoId: String, novoStatus: Int) {
        val updates = mapOf("status" to novoStatus)
        repository.atualizarJogo(jogoId, updates) { sucesso ->
            if (sucesso) {
                //Aqui vai recarregar a lista, ajudando na UI
                carregarBiblioteca(0)
                carregarBiblioteca(1)
                carregarBiblioteca(2)
            }
        }
    }
}
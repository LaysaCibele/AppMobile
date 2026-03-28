package com.example.appmobile.viewModel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appmobile.model.Usuario
import com.example.appmobile.repository.AuthRepository

class AuthViewModel : ViewModel() {
    private val repository = AuthRepository()

    val cadastroSucesso = MutableLiveData<Boolean>()
    val mensagemErro = MutableLiveData<String>()

    fun realizarCadastro(usuario: Usuario) {
        repository.cadastrar(usuario) { sucesso, erro ->
            if (sucesso) {
                cadastroSucesso.value = true
            } else {
                mensagemErro.value = erro ?: "Erro desconhecido"
            }
        }
    }

    fun realizarLogin(email: String, senha: String) {
        repository.logar(email, senha) { sucesso, erro ->
            if (sucesso) {
                cadastroSucesso.value = true
            } else {
                mensagemErro.value = erro ?: "Erro ao logar"
            }
        }
    }
}
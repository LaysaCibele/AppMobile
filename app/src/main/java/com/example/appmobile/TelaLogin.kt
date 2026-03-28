package com.example.appmobile

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.appmobile.viewModel.AuthViewModel

class TelaLogin : AppCompatActivity() {

    private lateinit var editTextUsuario: EditText
    private lateinit var editTextSenha: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tela_login)

        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)

        //ISSO OCULTA A ABA STATUS DO CELULAR
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                )

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        editTextEmail = findViewById(R.id.hintUsuario)
        editTextSenha = findViewById(R.id.hintSenha)
        val cadastreLogin = findViewById<TextView>(R.id.cadastreLogin)
        val botaoEntrar = findViewById<Button>(R.id.botaoParaBiblioteca)

        viewModel.cadastroSucesso.observe(this) { sucesso ->
            if (sucesso) {
                // Se o Firebase validou, vai levar o user para a biblio
                val intent = Intent(this, TelaBiblioteca::class.java)
                startActivity(intent)
                finish() // Impede de voltar ao login, caso já tenha logado
            }
        }

        viewModel.mensagemErro.observe(this) { erro ->
            // Se der erro (senha errada, etc), mostra o aviso
            Toast.makeText(this, erro, Toast.LENGTH_SHORT).show()
        }

        cadastreLogin.setOnClickListener {
            val intent = Intent(this, TelaCadastro::class.java)
            startActivity(intent)
        }

        botaoEntrar.setOnClickListener {
            val email = editTextEmail.text.toString().trim()
            val senha = editTextSenha.text.toString().trim()

            if (email.isEmpty()) {
                editTextEmail.error = "Digite o seu e-mail"
            } else if (senha.isEmpty()) {
                editTextSenha.error = "Digite a sua senha"
            } else {
                // Chama o login pelo firebase
                viewModel.realizarLogin(email, senha)
            }
        }
    }

    // se o usuário já estiver logado, não precisa logar de novo
    override fun onStart() {
        super.onStart()
    }

    }



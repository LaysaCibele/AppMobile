
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
    import com.example.appmobile.model.Usuario
    import com.example.appmobile.viewModel.AuthViewModel



    class TelaCadastro: AppCompatActivity() {

        private lateinit var editTextUsuario: EditText
        private lateinit var editTextEmail: EditText
        private lateinit var editTextSenha: EditText

        private lateinit var viewModel: AuthViewModel


        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            enableEdgeToEdge()
            setContentView(R.layout.activity_tela_cadastro);

            viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)


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


            editTextUsuario = findViewById(R.id.hintUsuario)
            editTextEmail = findViewById(R.id.hintEmail)
            editTextSenha = findViewById(R.id.hintSenha)
            val botaoParaLogin = findViewById<Button>(R.id.botaoParaLogin)
            val entreLogin = findViewById<TextView>(R.id.entreLogin)

            entreLogin.setOnClickListener {
                finish()
            }

            viewModel.cadastroSucesso.observe(this) { sucesso ->
                if (sucesso) {
                    Toast.makeText(this, "Conta criada com sucesso!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, TelaLogin::class.java))
                    finish()
                }
            }

            viewModel.mensagemErro.observe(this) { erro ->
                Toast.makeText(this, "Erro: $erro", Toast.LENGTH_LONG).show()
            }


            botaoParaLogin.setOnClickListener {
                val nome = editTextUsuario.text.toString().trim()
                val email = editTextEmail.text.toString().trim()
                val senha = editTextSenha.text.toString().trim()

                if (validarCampos(nome, email, senha)) {
                    val usuarioData = Usuario(nome = nome, email = email, senha = senha)
                    viewModel.realizarCadastro(usuarioData)
                }
            }
        }

        private fun validarCampos(nome: String, email: String, senha: String): Boolean {
            if (nome.isEmpty()) {
                editTextUsuario.error = "Nome obrigatório"
                return false
            }
            if (email.isEmpty()) {
                editTextEmail.error = "E-mail obrigatório"
                return false
            }
            // Nova verificação de formato de e-mail (checa @ e .com)
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                editTextEmail.error = "Digite um e-mail válido (ex: nome@email.com)"
                return false
            }
            if (senha.length < 6) {
                editTextSenha.error = "A senha precisa de 6 dígitos"
                return false
            }
            return true
        }
    }





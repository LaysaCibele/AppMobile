
    package com.example.appmobile

    import android.content.Intent
    import android.graphics.Outline
    import android.os.Bundle
    import android.text.method.HideReturnsTransformationMethod
    import android.view.View
    import android.widget.Button
    import android.widget.EditText
    import androidx.activity.enableEdgeToEdge
    import androidx.appcompat.app.AppCompatActivity
    import android.widget.ImageView
    import android.widget.TextView
    import androidx.core.view.ViewCompat
    import androidx.core.view.WindowInsetsCompat



    class TelaCadastro: AppCompatActivity() {

        private lateinit var editTextUsuario: EditText
        private lateinit var editTextEmail: EditText
        private lateinit var editTextSenha: EditText


        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            enableEdgeToEdge()


            window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            or View.SYSTEM_UI_FLAG_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    )

            setContentView(R.layout.activity_tela_cadastro);
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }

            val botaoParaLogin = findViewById<Button>(R.id.botaoParaLogin)
            botaoParaLogin.setOnClickListener {
                val intent = Intent(this, TelaLogin::class.java)
                startActivity(intent)

            }
            val entreLogin = findViewById<TextView>(R.id.entreLogin)
            entreLogin.setOnClickListener {
                val intent = Intent(this, TelaLogin::class.java)
                startActivity(intent)
            }

            editTextUsuario = findViewById(R.id.hintUsuario)
            editTextEmail = findViewById(R.id.hintEmail)
            editTextSenha = findViewById(R.id.hintSenha)

            botaoParaLogin.setOnClickListener() {
                val usuario = editTextUsuario.text.toString().trim()
                val email = editTextEmail.text.toString().trim()
                val senha = editTextSenha.text.toString().trim()

                if (usuario.isEmpty()) {
                    editTextUsuario.error = "Dados Obrigatórios!"
                    return@setOnClickListener
                } else if (email.isEmpty()) {
                    editTextEmail.error = "Dados Obrigatórios!"
                    return@setOnClickListener
                } else if (senha.isEmpty()) {
                    editTextSenha.error = "Dados Obrigatórios!"
                    return@setOnClickListener
                }
                val intent = Intent(this, TelaLogin::class.java)
                startActivity(intent)

            }
            }

        }





package com.example.appmobile

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appmobile.model.Jogo
import com.example.appmobile.repository.GameRepository

class TelaPesquisa : AppCompatActivity() {

    private val repository = GameRepository()
    private lateinit var rvCatalogo: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_pesquisa)

        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                )

        rvCatalogo = findViewById(R.id.rvCatalogoGeral)
        rvCatalogo.layoutManager = GridLayoutManager(this, 3)

        repository.buscarCatalogoGeral { listaJogos ->
            rvCatalogo.adapter = JogoAdapter(listaJogos) { jogoClicado ->
                val intent = Intent(this, TelaDetalhesJogo::class.java)
                intent.putExtra("JOGO_SELECIONADO", jogoClicado)
                intent.putExtra("VEIO_DO_CATALOGO", true)
                startActivity(intent)
            }
        }

        val botaoBiblioteca = findViewById<Button>(R.id.botaoBiblioteca)
        botaoBiblioteca.setOnClickListener {
            val intent = Intent(this, TelaBiblioteca::class.java)
            startActivity(intent)
        }

        //  ADICIONADO: MAPEAMENTO E LÓGICA DO BOTÃO SAIR NO CATÁLOGO
        val botaoSair = findViewById<Button>(R.id.botaoSair)
        botaoSair.setOnClickListener {
            // 1. Desloga o usuário da conta do Firebase de verdade
            com.google.firebase.auth.FirebaseAuth.getInstance().signOut()

            // 2. Prepara a intenção de ir para a TelaInicial
            val intent = Intent(this, TelaInicial::class.java)

            // 3. Esta flag limpa toda a pilha de telas na memória (Garante segurança máxima)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)
            finish() // Fecha a TelaPesquisa definitivamente
        }
    }

    // 5. FUNÇÃO DO POP-UP PARA ESCOLHER O STATUS
    private fun mostrarDialogoStatus(jogo: Jogo) {
        val opcoes = arrayOf("Quero Jogar", "Jogando", "Zerado")

        android.app.AlertDialog.Builder(this)
            .setTitle("Adicionar à sua estante")
            .setItems(opcoes) { _, which ->
                repository.salvarNoMeuInventario(jogo, which) { sucesso ->
                    if (sucesso) {
                        Toast.makeText(this, "${jogo.nome} adicionado!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Erro ao adicionar jogo.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .show()
    }
}
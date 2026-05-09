package com.example.appmobile


import android.content.Intent
import android.os.Bundle
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



        rvCatalogo = findViewById(R.id.rvCatalogoGeral)
        rvCatalogo.layoutManager = GridLayoutManager(this, 3)

        repository.buscarCatalogoGeral { listaJogos ->
            rvCatalogo.adapter = JogoAdapter(listaJogos) { jogoClicado ->
                mostrarDialogoStatus(jogoClicado)
            }
        }

        findViewById<Button>(R.id.botaoBiblioteca).setOnClickListener {

        val botaoBiblioteca= findViewById<Button>(R.id.botaoBiblioteca)
        botaoBiblioteca.setOnClickListener {
            val intent = Intent(this, TelaBiblioteca::class.java)
            startActivity(intent)
        }
    }

    // 5. FUNÇÃO DO POP-UP PARA ESCOLHER O STATUS
    private fun mostrarDialogoStatus(jogo: Jogo) {
        val opcoes = arrayOf("Quero Jogar", "Jogando", "Zerado")

        android.app.AlertDialog.Builder(this)
            .setTitle("Adicionar à sua estante")
            .setItems(opcoes) { _, which ->
                // 'which' envia 0, 1 ou 2 para o Firebase
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

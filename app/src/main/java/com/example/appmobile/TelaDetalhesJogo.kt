package com.example.appmobile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.appmobile.model.Jogo
import com.example.appmobile.repository.GameRepository

class TelaDetalhesJogo : AppCompatActivity() {

    private val repository = GameRepository()
    private lateinit var jogo: Jogo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_detalhes_jogo)

        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                )

        val jogoRecebido = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("JOGO_SELECIONADO", Jogo::class.java)
        } else {
            intent.getSerializableExtra("JOGO_SELECIONADO") as? Jogo
        }

        if (jogoRecebido == null) {
            Toast.makeText(this, "Erro ao carregar dados do jogo", Toast.LENGTH_SHORT).show()
            finish()
            return
        } else {
            jogo = jogoRecebido
        }

        val imgCapa = findViewById<ImageView>(R.id.imgCapaDetalhe)
        val txtTitulo = findViewById<TextView>(R.id.txtTituloDetalhe)
        val txtGenero = findViewById<TextView>(R.id.txtGeneroDetalhe)
        val txtDescricao = findViewById<TextView>(R.id.txtDescricaoDetalhe)
        val editAnotacoes = findViewById<EditText>(R.id.editAnotacoes)
        val btnSalvar = findViewById<Button>(R.id.btnSalvarAnotacoes)
        val btnWiki = findViewById<LinearLayout>(R.id.btnWiki)
        val btnMapa = findViewById<LinearLayout>(R.id.btnMapa)

        //  UI Dinamica
        txtTitulo.text = jogo.nome
        txtGenero.text = jogo.genero
        txtDescricao.text = jogo.descricao
        editAnotacoes.setText(jogo.anotacoes)

        Glide.with(this)
            .load(jogo.capaImagem)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .into(imgCapa)


        // Clique para abrir a Wiki
        btnWiki.setOnClickListener {
            if (jogo.linkWiki.isNotEmpty()) {
                val intentNavegador = Intent(Intent.ACTION_VIEW, Uri.parse(jogo.linkWiki))
                startActivity(intentNavegador)
            } else {
                Toast.makeText(this, "Wiki não disponível para este jogo", Toast.LENGTH_SHORT).show()
            }
        }

        // Clique para abrir o Mapa
        btnMapa.setOnClickListener {
            if (jogo.mapa.isNotEmpty()) {
                val intentNavegador = Intent(Intent.ACTION_VIEW, Uri.parse(jogo.mapa))
                startActivity(intentNavegador)
            } else {
                Toast.makeText(this, "Mapa interativo não disponível", Toast.LENGTH_SHORT).show()
            }
        }

        val btnVoltar = findViewById<ImageView>(R.id.btnVoltarDetalhe)

        btnVoltar.setOnClickListener {
            // Isso encerra a tela de detalhes e volta para a biblioteca que já estava aberta embaixo
            finish()
        }

        btnSalvar.setOnClickListener {
            val novasAnotacoes = editAnotacoes.text.toString()

            repository.atualizarAnotacoes(jogo.id, novasAnotacoes) { sucesso ->
                if (sucesso) {
                    Toast.makeText(this, "Anotações salvas!", Toast.LENGTH_SHORT).show()

                    // Isso evita a tela preta ao salvar a anotaçãop
                    finish()
                } else {
                    Toast.makeText(this, "Erro ao salvar no banco de dados.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
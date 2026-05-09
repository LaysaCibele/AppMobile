package com.example.appmobile

import android.R.id.toggle
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appmobile.model.Jogo
import androidx.recyclerview.widget.GridLayoutManager
import com.example.appmobile.repository.GameRepository
import com.example.appmobile.viewModel.GameViewModel
import android.widget.ImageButton

class TelaBiblioteca : AppCompatActivity() {
    private val viewModel: GameViewModel by viewModels()
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_tela_biblioteca)

            //ISSO OCULTA A ABA STATUS DO CELULAR
            window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            or View.SYSTEM_UI_FLAG_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    )

            val botaoPesquisa = findViewById<Button>(R.id.botaoPesquisa)
            botaoPesquisa.setOnClickListener {
                val intent = Intent(this, TelaPesquisa::class.java)
                startActivity(intent)
            }

            val botaoSair = findViewById<Button>(R.id.botaoSair)
            botaoSair.setOnClickListener {
                val intent = Intent(this, TelaInicial::class.java)
                startActivity(intent)
            }

            val rvQueroJogar = findViewById<RecyclerView>(R.id.rvQueroJogar)
            val rvJogando = findViewById<RecyclerView>(R.id.rvJogando)
            val rvZerados = findViewById<RecyclerView>(R.id.rvZerados)

            val headerQueroJogar = findViewById<RelativeLayout>(R.id.headerQueroJogar)
            val headerJogando = findViewById<RelativeLayout>(R.id.headerJogando)
            val headerZerados = findViewById<RelativeLayout>(R.id.headerZerados)

            val setaQueroJogar = findViewById<ImageView>(R.id.setaQueroJogar)
            val setaJogando = findViewById<ImageView>(R.id.setaJogando)
            val setaZerados = findViewById<ImageView>(R.id.setaZerados)

            rvQueroJogar.layoutManager = GridLayoutManager(this, 4)
            rvJogando.layoutManager = GridLayoutManager(this, 4)
            rvZerados.layoutManager = GridLayoutManager(this, 4)

            //conectando ao firebase
            viewModel.jogosQueroJogar.observe(this) { lista ->
                rvQueroJogar.adapter = JogoAdapter(lista) { jogo ->
                    val intent = Intent(this, TelaDetalhesJogo::class.java)
                    intent.putExtra("JOGO_SELECIONADO", jogo)
                    startActivity(intent)
                }
            }
            viewModel.jogosJogando.observe(this) { lista ->
                rvJogando.adapter = JogoAdapter(lista) { jogo ->
                    val intent = Intent(this, TelaDetalhesJogo::class.java)
                    intent.putExtra("JOGO_SELECIONADO", jogo)
                    startActivity(intent)
                }
            }
            viewModel.jogosZerados.observe(this) { lista ->
                rvZerados.adapter = JogoAdapter(lista) { jogo ->
                    val intent = Intent(this, TelaDetalhesJogo::class.java)
                    intent.putExtra("JOGO_SELECIONADO", jogo)
                    startActivity(intent)
                }
            }

            //aqui vai carregar os dados
            viewModel.carregarBiblioteca(0)
            viewModel.carregarBiblioteca(1)
            viewModel.carregarBiblioteca(2)

            headerQueroJogar.setOnClickListener { toggle(rvQueroJogar, setaQueroJogar) }
            headerJogando.setOnClickListener { toggle(rvJogando, setaJogando) }
            headerZerados.setOnClickListener { toggle(rvZerados, setaZerados) }

           /*
            val titulo = findViewById<TextView>(R.id.tituloBiblioteca)
            titulo.setOnClickListener {
                val repo = GameRepository()
                repo.criarJogoDeTeste { sucesso ->
                    if (sucesso) {
                        Log.d("DEBUG_APP", "Jogo salvo no Firebase com sucesso!")
                        Toast.makeText(this, "Jogo adicionado!", Toast.LENGTH_SHORT).show()

                        viewModel.carregarBiblioteca(1)
                    } else {
                        Log.d("DEBUG_APP", "Falha ao salvar no Firebase")
                        Toast.makeText(this, "Erro ao salvar no banco", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            //Essas linhas que comentei eu usei só para testar o Dinamic
            */


                }



        private fun toggle(recycler: RecyclerView, seta: ImageView) {

            if (recycler.visibility == View.GONE) {
                recycler.visibility = View.VISIBLE
                seta.rotation = 180f
            } else {
                recycler.visibility = View.GONE
                seta.rotation = 0f
            }
        }
    }

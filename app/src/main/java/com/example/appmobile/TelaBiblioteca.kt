package com.example.appmobile

import android.R.id.toggle
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appmobile.model.Jogo
import androidx.recyclerview.widget.GridLayoutManager
import com.example.appmobile.viewModel.GameViewModel

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
                rvQueroJogar.adapter = JogoAdapter(lista)
            }
            viewModel.jogosJogando.observe(this) { lista ->
                rvJogando.adapter = JogoAdapter(lista)
            }
            viewModel.jogosZerados.observe(this) { lista ->
                rvZerados.adapter = JogoAdapter(lista)
            }

            //aqui vai carregar os dados
            viewModel.carregarBiblioteca(0)
            viewModel.carregarBiblioteca(1)
            viewModel.carregarBiblioteca(2)

            headerQueroJogar.setOnClickListener { toggle(rvQueroJogar, setaQueroJogar) }
            headerJogando.setOnClickListener { toggle(rvJogando, setaJogando) }
            headerZerados.setOnClickListener { toggle(rvZerados, setaZerados) }
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

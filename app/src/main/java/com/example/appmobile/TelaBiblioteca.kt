package com.example.appmobile

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.GridLayoutManager

class TelaBiblioteca : AppCompatActivity() {

    // LISTA DE JOGOS PARA TESTE
    private val listaJogos = listOf(
        Jogo(R.drawable.arc_raiders),
        Jogo(R.drawable.stardew),
        Jogo(R.drawable.terraria),
        Jogo(R.drawable.silksong),
        Jogo(R.drawable.silksong),
        Jogo(R.drawable.silksong)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_biblioteca)

        //ISSO OCULTA A ABA STATUS DO CELULAR
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                )

        val botaoPesquisa= findViewById<Button>(R.id.botaoPesquisa)
        botaoPesquisa.setOnClickListener {
            val intent = Intent(this, TelaPesquisa::class.java)
            startActivity(intent)}

        // QUERO JOGAR
        val headerQueroJogar = findViewById<RelativeLayout>(R.id.headerQueroJogar)
        val rvQueroJogar = findViewById<RecyclerView>(R.id.rvQueroJogar)
        val setaQueroJogar = findViewById<ImageView>(R.id.setaQueroJogar)

        rvQueroJogar.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        rvQueroJogar.adapter = JogoAdapter(listaJogos)

        headerQueroJogar.setOnClickListener {
            toggle(rvQueroJogar, setaQueroJogar)
        }

        // JOGANDO
        val headerJogando = findViewById<RelativeLayout>(R.id.headerJogando)
        val rvJogando = findViewById<RecyclerView>(R.id.rvJogando)
        val setaJogando = findViewById<ImageView>(R.id.setaJogando)

        rvJogando.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        rvJogando.adapter = JogoAdapter(listaJogos)

        headerJogando.setOnClickListener {
            toggle(rvJogando, setaJogando)
        }

        // ZERADOS
        val headerZerados = findViewById<RelativeLayout>(R.id.headerZerados)
        val rvZerados = findViewById<RecyclerView>(R.id.rvZerados)
        val setaZerados = findViewById<ImageView>(R.id.setaZerados)

        rvQueroJogar.layoutManager = GridLayoutManager(this, 4)
        rvQueroJogar.adapter = JogoAdapter(listaJogos)

        rvJogando.layoutManager = GridLayoutManager(this, 4)
        rvJogando.adapter = JogoAdapter(listaJogos)

        rvZerados.layoutManager = GridLayoutManager(this, 4)
        rvZerados.adapter = JogoAdapter(listaJogos)

        /* GRID HORIZONTAL
        rvZerados.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

            (isso aq a gente pode usar para a aba de search futuramente)
        */

        headerZerados.setOnClickListener {
            toggle(rvZerados, setaZerados)
        }
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
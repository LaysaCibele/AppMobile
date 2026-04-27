package com.example.appmobile

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager


class TelaPesquisa : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_pesquisa)

        val botaoBiblioteca= findViewById<Button>(R.id.botaoBiblioteca)
        botaoBiblioteca.setOnClickListener {
            val intent = Intent(this, TelaBiblioteca::class.java)
            startActivity(intent)}

        val rvEmAlta = findViewById<RecyclerView>(R.id.rvEmAlta)
        val rvIndie = findViewById<RecyclerView>(R.id.rvIndie)
        val rvSimulacao = findViewById<RecyclerView>(R.id.rvSimulacao)

        rvEmAlta.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        rvIndie.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        rvSimulacao.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)


        val listaEmAlta = listOf(
            Jogo(R.drawable.arc_raiders),
            Jogo(R.drawable.cs),
            Jogo(R.drawable.alice),
            Jogo(R.drawable.farcry),
            Jogo(R.drawable.euro_truck),
            Jogo(R.drawable.elden_ring),
            Jogo(R.drawable.house_flipper),
            Jogo(R.drawable.dishonored2)
        )

        val listaIndie= listOf(
            Jogo(R.drawable.helldivers),
            Jogo(R.drawable.farcry),
            Jogo(R.drawable.house_flipper)
        )

        val listaSimulacao= listOf(
            Jogo(R.drawable.elden_ring)
        )

        rvEmAlta.adapter = JogoAdapter(listaEmAlta)
        rvIndie.adapter = JogoAdapter(listaIndie)
        rvSimulacao.adapter = JogoAdapter(listaSimulacao)














    }
}

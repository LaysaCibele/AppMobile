package com.example.appmobile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView

//KT QUE RECONHECE A CAPA E O JOGO RECEBIDOS DO FUTURO BANCO DE DADOS
//Como n temos ainda o banco de dados eu coloquei uma lista temporaria das fots q temos
class JogoAdapter(private val listaJogos: List<Jogo>) :
    RecyclerView.Adapter<JogoAdapter.JogoViewHolder>() {

    class JogoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgCapaJogo: ShapeableImageView =
            itemView.findViewById(R.id.imgCapaJogo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JogoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_jogo, parent, false)
        return JogoViewHolder(view)
    }

    override fun onBindViewHolder(holder: JogoViewHolder, position: Int) {
        val jogo = listaJogos[position]
        holder.imgCapaJogo.setImageResource(jogo.capa)
    }

    override fun getItemCount(): Int {
        return listaJogos.size
    }
}
package com.example.appmobile

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.example.appmobile.model.Jogo
import com.bumptech.glide.Glide

class JogoAdapter(private val listaJogos: List<Jogo>) :
    RecyclerView.Adapter<JogoAdapter.JogoViewHolder>() {

    class JogoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgCapaJogo: ShapeableImageView = itemView.findViewById(R.id.imgCapaJogo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JogoViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_jogo, parent, false)
        return JogoViewHolder(view)
    }

    override fun onBindViewHolder(holder: JogoViewHolder, position: Int) {
        val jogo = listaJogos[position]

        Glide.with(holder.itemView.context)
            .load(jogo.capaImagem)
            .placeholder(android.R.drawable.ic_menu_gallery)
            .into(holder.imgCapaJogo)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, TelaDetalhesJogo::class.java)

            intent.putExtra("JOGO_SELECIONADO", jogo)


            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return listaJogos.size
    }
}
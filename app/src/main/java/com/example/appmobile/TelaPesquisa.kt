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
import com.google.firebase.Firebase


class TelaPesquisa : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_pesquisa)

        Firebase

        val botaoBiblioteca= findViewById<Button>(R.id.botaoBiblioteca)
        botaoBiblioteca.setOnClickListener {
            val intent = Intent(this, TelaBiblioteca::class.java)
            startActivity(intent)}
        
    }
}

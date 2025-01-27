package com.iacc.robert_morales_semana_4

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ListView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val cameras = mutableListOf(
            CamaraClass("Andowl","Modelo : QS66","Valor $ 44.191",R.drawable.andowl),
            CamaraClass("Yoosee","Modelo : camara ip","Valor $ 61.740",R.drawable.yoosee),
            CamaraClass("Ation","Modelo : 1005","Valor $ 33.486",R.drawable.ation),
            CamaraClass("Anran","Modelo : N30W1545","Valor $ 61.698",R.drawable.anran),
            CamaraClass("Zeker","Modelo : Doble Zeker","Valor $ 84.990",R.drawable.zeker),
            CamaraClass("Genérica","Modelo : DualSolar","$ 64.990",R.drawable.zeker)

        )

        val listView = findViewById<ListView>(R.id.listView)
        val adapter = CamaraAdaptador(this, cameras)
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedCamera = cameras[position]
            val intent = Intent(this, CamaraDetalleItems::class.java)

            intent.putExtra("imageRes", selectedCamera.imagen)
            intent.putExtra("marca", selectedCamera.marca)
            intent.putExtra("modelo", selectedCamera.modelo)
            intent.putExtra("precio", selectedCamera.precio)

            startActivity(intent)
        }

    }

}

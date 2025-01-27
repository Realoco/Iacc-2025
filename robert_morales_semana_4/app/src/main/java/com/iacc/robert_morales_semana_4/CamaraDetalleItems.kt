package com.iacc.robert_morales_semana_4

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import  com.iacc.robert_morales_semana_4.databinding.ActivityCameraDetailsBinding

class CamaraDetalleItems : AppCompatActivity() {

    private lateinit var binding: ActivityCameraDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Detalle de Cámara"


        val imageRes = intent.getIntExtra("imageRes", 0)
        val brand = intent.getStringExtra("marca")
        val modelo = intent.getStringExtra("modelo")
        val price = intent.getStringExtra("precio")

        // Mostrar los datos en la vista
        binding.imageViewDetail.setImageResource(imageRes)
        binding.valmarca.text = "Marca" + brand
        binding.valmodelo.text = modelo
        binding.valprecio.text = price
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}

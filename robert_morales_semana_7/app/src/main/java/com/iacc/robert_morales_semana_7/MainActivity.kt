package com.iacc.robert_morales_semana_7

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val apiService = ApiService.create()


        apiService.getBitcoinData().enqueue(object : Callback<BitcoinData> {
            override fun onResponse(call: Call<BitcoinData>, response: Response<BitcoinData>) {
                if (response.isSuccessful) {
                    val bitcoinData = response.body()
                    displayBitcoinData(bitcoinData)
                } else {
                    Toast.makeText(this@MainActivity, "Error al cargar los datos de Bitcoin", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<BitcoinData>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Fallo la conexión", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun displayBitcoinData(bitcoinData: BitcoinData?) {
        val textView = findViewById<TextView>(R.id.textViewBitcoin)

        bitcoinData?.let {

            val bitcoinInfo = it.serie.joinToString("\n") { serie ->
                "${it.nombre}: ${serie.valor} ${it.unidad_medida}"
            }
            textView.text = bitcoinInfo
        }
    }
}

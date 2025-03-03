package com.iacc.robert_morales_semana_7

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class WelcomeActivity : AppCompatActivity() {

    private lateinit var welcomeTextView: TextView
    private lateinit var holidaysListView: ListView
    private lateinit var closeButton: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        welcomeTextView = findViewById(R.id.tvWelcome)
        holidaysListView = findViewById(R.id.lvHolidays)
        closeButton = findViewById(R.id.btnClose)

        val sharedPreferences = getSharedPreferences("user_data", MODE_PRIVATE)
        val username = sharedPreferences.getString("username", "Usuario desconocido")

        welcomeTextView.text = "¡Bienvenido, $username!"

        loadBitcoinData()

        closeButton.setOnClickListener {
            logout()
        }
    }

    private fun logout() {

        val sharedPreferences: SharedPreferences = getSharedPreferences("user_data", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()


        Toast.makeText(this, "¡Has cerrado sesión!", Toast.LENGTH_SHORT).show()


        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)


        finish()
    }

    private fun loadBitcoinData() {

        val apiService = ApiService.create()


        apiService.getBitcoinData().enqueue(object : Callback<BitcoinData> {
            override fun onResponse(call: Call<BitcoinData>, response: Response<BitcoinData>) {
                if (response.isSuccessful) {
                    val bitcoinData = response.body()
                    displayBitcoinData(bitcoinData)
                } else {
                    Toast.makeText(this@WelcomeActivity, "Error al cargar los datos de Bitcoin", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<BitcoinData>, t: Throwable) {
                Toast.makeText(this@WelcomeActivity, "Fallo la conexión", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun displayBitcoinData(bitcoinData: BitcoinData?) {
        bitcoinData?.let {
            val inputFormatter = DateTimeFormatter.ISO_ZONED_DATE_TIME
            val outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.getDefault())



            val bitcoinValues =
                it.serie .sortedByDescending { item -> ZonedDateTime.parse(item.fecha, inputFormatter) }
                    .map { item ->
                        val formattedDate = try {
                            ZonedDateTime.parse(item.fecha, inputFormatter).format(outputFormatter)
                        } catch (e: Exception) {
                            item.fecha
                        }
                        "$formattedDate: ${item.valor} ${it.unidad_medida}"
                    }


            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, bitcoinValues)
            holidaysListView.adapter = adapter
        }
    }


}

package com.iacc.robert_morales_semana_7

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etUsername: EditText = findViewById(R.id.etUsername)
        val etPassword: EditText = findViewById(R.id.etPassword)
        val btnLogin: Button = findViewById(R.id.btnLogin)


        val sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val savedUsername = sharedPreferences.getString("username", "")
        val savedPassword = sharedPreferences.getString("password", "")


        if (!savedUsername.isNullOrEmpty()) {
            etUsername.setText(savedUsername)
        }
        if (!savedPassword.isNullOrEmpty()) {
            etPassword.setText(savedPassword)
        }

        btnLogin.setOnClickListener {
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()


            if (username.isNotEmpty() && password.isNotEmpty()) {

                val editor = sharedPreferences.edit()
                editor.putString("username", username)
                editor.putString("password", password)
                editor.putBoolean("isLoggedIn", true)
                editor.apply()


                Toast.makeText(this, "Bienvenido, $username", Toast.LENGTH_SHORT).show()


                val intent = Intent(this, WelcomeActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Por favor ingresa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

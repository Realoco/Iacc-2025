package com.iacc.robert_morales_semana_5

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.iacc.robert_morales_semana_5.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            val fragment: Fragment = when (item.itemId) {
                R.id.nav_Listavendedor -> vendedorFragment()
                R.id.nav_TomarFoto -> tomarFotoFragment()
                R.id.nav_VendedorGrafico -> VendedorGraficoFragment()
                else -> vendedorFragment()
            }
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
            true
        }


        binding.bottomNavigationView.selectedItemId = R.id.nav_Listavendedor
    }
}

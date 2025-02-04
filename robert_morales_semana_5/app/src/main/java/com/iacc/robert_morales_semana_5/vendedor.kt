package com.iacc.robert_morales_semana_5
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
class vendedorFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_vendedor, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerVendedores)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val vendedores = listOf(
            Vendedor("Robert Morales", "Ventas Local", R.drawable.vendor_1),
            Vendedor("Diego fernandez", "Ventas internet", R.drawable.vendor_2),
            Vendedor("Isidora Morales", "Ventas Local", R.drawable.vendor_3),
            Vendedor("Benjamin Morales", "Ventas internet\"", R.drawable.vendor_4),
            Vendedor("Pedro Morales", "Ventas Local", R.drawable.vendor_5)
        )
        recyclerView.adapter = VendedorAdapter(vendedores)
        return view
    }
}
data class Vendedor(
    val nombre: String,
    val area: String,
    val foto: Int
)
package com.iacc.robert_morales_semana_5

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.iacc.robert_morales_semana_5.databinding.FragmentVendedorGraficoBinding
import java.text.NumberFormat
import java.util.Locale

class VendedorGraficoFragment : Fragment() {

    private var _binding: FragmentVendedorGraficoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVendedorGraficoBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val barChart = binding.barChart

        val entries = listOf(
            BarEntry(0f, 1000000f),  // Robert Morales
            BarEntry(1f, 2500000f),  // Diego fernandez
            BarEntry(2f, 1800000f),  // Isidora Morales
            BarEntry(3f, 3200000f),  // Benjamin Morales
            BarEntry(4f, 2900000f)   // Pedro Morales
        )

        val nombresVendedores = listOf("Robert", "Diego", "Isidora", "Benjamin", "Pedro")

        val dataSet = BarDataSet(entries, "Ventas por Vendedor")
        dataSet.color = Color.BLUE
        dataSet.valueTextColor = Color.BLACK
        dataSet.valueFormatter = CurrencyValueFormatter()

        val barData = BarData(dataSet)
        barData.barWidth = 0.5f
        barChart.data = barData

        barChart.xAxis.apply {
            valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return if (value.toInt() in nombresVendedores.indices) {
                        nombresVendedores[value.toInt()]
                    } else {
                        value.toString()
                    }
                }
            }
            position = XAxis.XAxisPosition.BOTTOM
            granularity = 1f  // Asegurar que los valores sean enteros
            setDrawGridLines(false)
        }

        // Configuración del eje Y (valores de ventas con formato de moneda)
        barChart.axisLeft.apply {
            valueFormatter = CurrencyValueFormatter()
            axisMinimum = 0f // Evitar valores negativos
        }
        barChart.axisRight.isEnabled = false // Ocultar eje derecho

        barChart.description.isEnabled = false // Ocultar descripción
        barChart.invalidate() // Refrescar gráfico
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    class CurrencyValueFormatter : ValueFormatter() {
        private val formatter: NumberFormat = NumberFormat.getCurrencyInstance(Locale("es", "CL"))

        init {
            formatter.maximumFractionDigits = 0
        }

        override fun getFormattedValue(value: Float): String {
            return formatter.format(value)
        }
    }
}

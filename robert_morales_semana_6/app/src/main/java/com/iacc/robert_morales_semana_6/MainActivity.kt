package com.iacc.robert_morales_semana_6

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.iacc.robert_morales_semana_6.database.DatabaseHelper
import com.iacc.robert_morales_semana_6.entidades.Infraccion

class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var listView: ListView
    private lateinit var adapter: ArrayAdapter<String>
    private var infraccionesList = mutableListOf<String>()
    private var infracciones: MutableList<Infraccion> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = DatabaseHelper(this)
        listView = findViewById(R.id.listViewInfracciones)
        val btnAgregar = findViewById<Button>(R.id.btnAgregar)
        val btnCompartir = findViewById<Button>(R.id.btnCompartir)

        cargarInfracciones()

        btnAgregar.setOnClickListener {
            mostrarDialogoAgregar()
        }

           listView.setOnItemClickListener { _, _, position, _ ->
            mostrarDialogoEditar(infracciones[position])
        }

        btnCompartir.setOnClickListener {
            val infraccionSeleccionada = infracciones.getOrNull(0)
            infraccionSeleccionada?.let { infraccion ->
                compartirInfraccion(infraccion)
            }
        }
    }

    private fun cargarInfracciones() {
        infracciones = dbHelper.obtenerInfracciones().toMutableList()
        infraccionesList.clear()
        infraccionesList.addAll(infracciones.map { "Folio ${it.id}: ${it.nombreLocal} - ${it.infraccion}" })

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, infraccionesList)
        listView.adapter = adapter
    }

    private fun mostrarDialogoAgregar() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Agregar Nueva Infracción")

        val view = layoutInflater.inflate(R.layout.dialog_add_infraccion, null)
        builder.setView(view)

        builder.setPositiveButton("Guardar") { _, _ ->
            val rut = view.findViewById<EditText>(R.id.etRutInspector).text.toString()
            val local = view.findViewById<EditText>(R.id.etNombreLocal).text.toString()
            val direccion = view.findViewById<EditText>(R.id.etDireccion).text.toString()
            val infraccion = view.findViewById<EditText>(R.id.etInfraccion).text.toString()

            val nuevaInfraccion = Infraccion(0, rut, local, direccion, infraccion)
            val folioCreado = dbHelper.insertarInfraccion(nuevaInfraccion)
            Toast.makeText(this, "El folio de la infraccion Creada es : " + folioCreado, Toast.LENGTH_SHORT).show()
            cargarInfracciones()
        }
        builder.setNegativeButton("Cancelar", null)
        builder.show()
    }
   private fun mostrarDialogoEditar(infraccion: Infraccion) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Editar Infracción")
        val view = layoutInflater.inflate(R.layout.dialog_edit_infraccion, null)
        builder.setView(view)
        val textFolio = view.findViewById<TextView>(R.id.textFolio)
        val editRut = view.findViewById<EditText>(R.id.etRutInspector)
        val editNombre = view.findViewById<EditText>(R.id.etNombreLocal)
        val editDireccion = view.findViewById<EditText>(R.id.etDireccion)
        val editInfraccion = view.findViewById<EditText>(R.id.etInfraccion)

        textFolio.text = "Folio: ${infraccion.id}"
        editRut.setText(infraccion.rutInspector)
        editNombre.setText(infraccion.nombreLocal)
        editDireccion.setText(infraccion.direccion)
        editInfraccion.setText(infraccion.infraccion)
        builder.setPositiveButton("Guardar") { _, _ ->
            dbHelper.actualizarInfraccion(
                infraccion.id,
                editRut.text.toString(),
                editNombre.text.toString(),
                editDireccion.text.toString(),
                editInfraccion.text.toString()
            )
            cargarInfracciones()
        }
        builder.setNegativeButton("Cancelar", null)
        builder.show()
    }

    private fun compartirInfraccion(infraccion: Infraccion) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"

        val message = """
            Infracción
            Folio: ${infraccion.id}
            Rut Inspector: ${infraccion.rutInspector}
            Nombre Local Comercial: ${infraccion.nombreLocal}
            Dirección: ${infraccion.direccion}
            Infracción: ${infraccion.infraccion}
        """.trimIndent()

        intent.putExtra(Intent.EXTRA_TEXT, message)
        startActivity(Intent.createChooser(intent, "Compartir infracción"))
    }
}

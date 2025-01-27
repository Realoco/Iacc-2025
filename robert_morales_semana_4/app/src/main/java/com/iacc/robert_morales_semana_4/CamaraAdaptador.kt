package com.iacc.robert_morales_semana_4

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu

class CamaraAdaptador(
    private val context: Context,
    private val cameras: MutableList<CamaraClass>
) : ArrayAdapter<CamaraClass>(context, 0, cameras) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.listar_item_camaras, parent, false)

        val imageView = view.findViewById<ImageView>(R.id.imageView)
        val textView = view.findViewById<TextView>(R.id.marca)
        val menuButton = view.findViewById<ImageView>(R.id.menuButton)

        val camera = cameras[position]
        imageView.setImageResource(camera.imagen)
        textView.text = camera.marca

        menuButton.setOnClickListener {
            val popupMenu = PopupMenu(context, menuButton)
            popupMenu.menuInflater.inflate(R.menu.menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.delete -> {
                        cameras.removeAt(position)
                        notifyDataSetChanged()
                        true
                    }
                    else -> false
                }
            }
            popupMenu.show()
        }

        return view
    }
}

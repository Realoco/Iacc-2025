package com.iacc.robert_morales_semana_5

import android.media.MediaPlayer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class VendedorAdapter(private val listaVendedores: List<Vendedor>) :
    RecyclerView.Adapter<VendedorAdapter.VendedorViewHolder>() {

    class VendedorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgVendedor: ImageView = view.findViewById(R.id.imgVendedor)
        val tvNombre: TextView = view.findViewById(R.id.tvNombre)
        val tvArea: TextView = view.findViewById(R.id.tvArea)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VendedorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_vendedor, parent, false)
        return VendedorViewHolder(view)
    }

    override fun onBindViewHolder(holder: VendedorViewHolder, position: Int) {
        val vendedor = listaVendedores[position]
        holder.tvNombre.text = vendedor.nombre
        holder.tvArea.text = vendedor.area
        holder.imgVendedor.setImageResource(vendedor.foto)

        holder.itemView.setOnClickListener {
            if (position == 2) {
                val mediaPlayer = MediaPlayer.create(holder.itemView.context, R.raw.vender_ropa)
                mediaPlayer.start()
            }
        }
    }

    override fun getItemCount() = listaVendedores.size
}

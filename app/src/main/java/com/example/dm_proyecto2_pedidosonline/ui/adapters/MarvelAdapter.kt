package com.example.dm_proyecto2_pedidosonline.ui.adapters

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dm_proyecto2_pedidosonline.Logic.lists.listItems
import com.example.dm_proyecto2_pedidosonline.R
import com.example.dm_proyecto2_pedidosonline.data.entities.marvel.MarvelChars
import com.example.dm_proyecto2_pedidosonline.databinding.MarvelCharactersBinding
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso

class MarvelAdapter(private val items: List<MarvelChars>) :
    RecyclerView.Adapter<MarvelAdapter.MarvelViewHolder>() {
    class MarvelViewHolder(view: View):RecyclerView.ViewHolder(view){
        private val binding:MarvelCharactersBinding=MarvelCharactersBinding.bind(view)
        fun render(item: MarvelChars){
            println("Recibiendo a ${item.nombre}")
            binding.txtTitulo.text = item.nombre
            binding.txtComic.text=item.comic
            Picasso.get().load(item.imagen).into(binding.imgMarvel)

            binding.imgMarvel.setOnClickListener {
                Snackbar.make(
                    binding.imgMarvel,
                    item.nombre,
                    Snackbar.LENGTH_SHORT
                )
                    .show()
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int)
        : MarvelViewHolder {
            val inflater=LayoutInflater.from(parent.context)
            return MarvelViewHolder(inflater.inflate(R.layout.marvel_characters,parent,false))
    }

    override fun getItemCount(): Int {
        return items.size
    }
    override fun onBindViewHolder(holder: MarvelAdapter.MarvelViewHolder, position: Int) {
        holder.render(items[position])
    }
}
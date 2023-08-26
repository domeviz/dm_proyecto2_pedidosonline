package com.example.dm_proyecto2_pedidosonline.ui.adapters

import android.util.Log
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

class MarvelAdapter(
    private var fnClick: (MarvelChars) -> Unit
) :
    RecyclerView.Adapter<MarvelAdapter.MarvelViewHolder>() {
    var items:List<MarvelChars> = listOf()
    class MarvelViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding: MarvelCharactersBinding = MarvelCharactersBinding.bind(view)
        fun render(item: MarvelChars,
                   fnClick: (MarvelChars) -> Unit) {
            binding.txtTitulo.text = item.nombre
            binding.txtComic.text = item.comic
            Picasso.get().load(item.imagen).into(binding.imgMarvel)

            itemView.setOnClickListener {
                fnClick(item)
            }

            binding.imgMarvel.setOnClickListener {
                fnClick(item)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MarvelAdapter.MarvelViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MarvelViewHolder(inflater.inflate(R.layout.marvel_characters,
            parent,
            false))
    }

    override fun getItemCount(): Int =items.size

    fun updateListItems(newItems: List<MarvelChars>){
        this.items =this.items.plus(newItems)
        notifyDataSetChanged()

    }
    fun replaceListAdapter(newItems: List<MarvelChars>) {
        this.items = newItems
        notifyDataSetChanged()

    }
    override fun onBindViewHolder(holder: MarvelAdapter.MarvelViewHolder, position: Int) {
        holder.render(items[position], fnClick)
    }
}
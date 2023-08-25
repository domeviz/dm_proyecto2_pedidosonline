package com.example.dm_proyecto2_pedidosonline.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.dm_proyecto2_pedidosonline.Logic.lists.listItems
import com.example.dm_proyecto2_pedidosonline.R
import com.example.dm_proyecto2_pedidosonline.data.entities.marvel.MarvelChars
import com.example.dm_proyecto2_pedidosonline.databinding.MarvelCharactersBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso

class FavoritosAdapter(private var user: String,
                       private var fnClick: (MarvelChars) -> Unit) :
    RecyclerView.Adapter<FavoritosAdapter.FavoritosViewHolder>() {
    var items:List<MarvelChars> = listOf()
    class FavoritosViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding: MarvelCharactersBinding = MarvelCharactersBinding.bind(view)

        //Conexion a la firestore
        private var fireBase = Firebase.firestore

        fun render(item: MarvelChars, fnClick: (MarvelChars) -> Unit, user: String) {
            //El adapter modifica segun lo que llama
            binding.txtTitulo.text = item.nombre
            binding.txtComic.text = item.comic
            Picasso.get().load(item.imagen).into(binding.imgMarvel)
            binding.btnSave.setOnClickListener {
                Snackbar.make(binding.root,"Se agregó a favoritos el personaje: ${item.nombre}",Snackbar.LENGTH_SHORT).show()
                insertarFavorito(item,user)
            }
        }

        fun insertarFavorito(item: MarvelChars, user: String) {
            // Create a new user with a first, middle, and last name
            val TAG = "Marvel"
            item.user = user
            // Add a new document with a generated ID
            fireBase.collection("personajes")
                .add(item)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoritosAdapter.FavoritosViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        return FavoritosViewHolder(
            inflater.inflate(
                R.layout.marvel_characters,
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: FavoritosAdapter.FavoritosViewHolder, position: Int) {
        holder.render(items[position],fnClick, user)
    }

    override fun getItemCount(): Int = items.size

    fun updateData(newItems: List<MarvelChars>) {
        items = newItems
        notifyDataSetChanged()
    }
}
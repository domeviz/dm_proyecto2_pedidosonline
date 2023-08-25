package com.example.dm_proyecto2_pedidosonline.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.dm_proyecto2_pedidosonline.R
import com.example.dm_proyecto2_pedidosonline.data.entities.marvel.MarvelChars
import com.example.dm_proyecto2_pedidosonline.databinding.FragmentFirstBinding
import com.example.dm_proyecto2_pedidosonline.databinding.FragmentSecondBinding
import com.example.dm_proyecto2_pedidosonline.ui.activities.DetailsMarvelItem
import com.example.dm_proyecto2_pedidosonline.ui.adapters.FavoritosAdapter
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SecondFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SecondFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var fireBase = Firebase.firestore
    private lateinit var binding: FragmentSecondBinding

    private lateinit var gManager: GridLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecondBinding.inflate(layoutInflater, container, false)
        // Inflate the layout for this fragment
        gManager = GridLayoutManager(requireContext(), 2)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val user = arguments?.getString("user")
        if (user != null) {
//            binding.shimmer.visibility = View.VISIBLE
            getData(user)
        }
    }
    private fun getData(user:String) {
        val adapter = FavoritosAdapter(""
        ) { sendFavoritosItem(it) }
        val rvDatos = binding.rvMarvelChars
        rvDatos.adapter = adapter
        binding.rvMarvelChars.apply {
            this.adapter = adapter // Usar el adaptador 'adapter' aquí
            this.layoutManager = gManager
        }

        // Llamar a getEventos y actualizar el adaptador con los datos obtenidos
        getPersonajes(user) { eventos ->
            adapter.updateData(eventos)
        }
    }

    private fun sendFavoritosItem(item: MarvelChars) {
        val i = Intent(requireActivity(), DetailsMarvelItem::class.java)
        i.putExtra("name", item)
        startActivity(i)
    }
    private fun getPersonajes(user: String, callback: (ArrayList<MarvelChars>) -> Unit) {
        val TAG = "Marvel Firebase"
        val eventosList = ArrayList<MarvelChars>()
        fireBase.collection("personajes")
            .whereEqualTo("user", user)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val data = document.data
                    eventosList.add(
                        MarvelChars(
                            0,
                            data["nombre"] as String,
                            data["comic"] as String,
                            data["imagen"] as String,
                            ""
                        )
                    )
                }
                callback(eventosList)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }
}
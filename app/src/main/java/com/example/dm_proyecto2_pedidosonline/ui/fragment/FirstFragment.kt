package com.example.dm_proyecto2_pedidosonline.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dm_proyecto2_pedidosonline.Logic.validator.ListItems
import com.example.dm_proyecto2_pedidosonline.R
import com.example.dm_proyecto2_pedidosonline.databinding.FragmentFirstBinding
import com.example.dm_proyecto2_pedidosonline.ui.adapters.MarvelAdapter

/**
 * A simple [Fragment] subclass.
 * Use the [FirstFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FirstFragment : Fragment() {

    private lateinit var binding : FragmentFirstBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentFirstBinding.inflate(layoutInflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        /*val names= arrayListOf<String>("Rosa","Pepe","Antonio","Carlos","Juan","Xavier", "Andres")
        var adapter=ArrayAdapter<String>(
            requireActivity(),
            R.layout.simple_spinner,
            names
        )

        binding.spinner.adapter=adapter*/

        val rvAdapter=MarvelAdapter(ListItems().returnMarvelChars())

        val rvMarvel=binding.rvMarvelChars
        rvMarvel.adapter=rvAdapter
        rvMarvel.layoutManager=LinearLayoutManager(
            requireActivity(), LinearLayoutManager.VERTICAL,
            false
        )
    }

}
package com.example.dm_proyecto2_pedidosonline.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.dm_proyecto2_pedidosonline.R
import com.example.dm_proyecto2_pedidosonline.databinding.FragmentFirstBinding
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dm_proyecto2_pedidosonline.Logic.lists.listItems
import com.example.dm_proyecto2_pedidosonline.ui.adapters.MarvelAdapter

/**
 * A simple [Fragment] subclass.
 * Use the [FirstFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FirstFragment : Fragment() {

    private lateinit var binding :FragmentFirstBinding

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
        val names= arrayListOf<String>("Spiderman","Invisible Woman","Eternity","Black Widow")
        val adapter=ArrayAdapter<String>(
            requireActivity(),
            R.layout.simple_spinner,
            names
        )
        binding.spinner.adapter=adapter
        //binding.listView.adapter=adapter
        val rvAdapter= MarvelAdapter(listItems().returnMarvelChars())
        val rvMarvel =binding.rvMarvelChars
        rvMarvel.adapter=rvAdapter
        rvMarvel.layoutManager= LinearLayoutManager(requireActivity(),LinearLayoutManager.VERTICAL,false
        )

    }

}
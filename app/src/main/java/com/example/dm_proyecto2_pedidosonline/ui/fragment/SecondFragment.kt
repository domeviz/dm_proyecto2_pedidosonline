package com.example.dm_proyecto2_pedidosonline.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.dm_proyecto2_pedidosonline.R
import com.example.dm_proyecto2_pedidosonline.databinding.FragmentFirstBinding
import com.example.dm_proyecto2_pedidosonline.databinding.FragmentSecondBinding

/**
 * A simple [Fragment] subclass.
 * Use the [SecondFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SecondFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var binding : FragmentSecondBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentSecondBinding.inflate(layoutInflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }
    override fun onStart() {
        super.onStart()
        val nombres = arrayListOf<String>("Rosa","Pepe","Antonio","Carlos","Juan","Xavier", "Andres")

        val adapter= ArrayAdapter<String>(
            requireActivity(),
            /*android.R.layout.simple_spinner_item, */
            R.layout.simple_layout,
            nombres)
        binding.spinn.adapter=adapter
    }
}
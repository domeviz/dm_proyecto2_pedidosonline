package com.example.dm_proyecto2_pedidosonline.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dm_proyecto2_pedidosonline.Logic.marvelLogic.MarvelLogic
import com.example.dm_proyecto2_pedidosonline.R
import com.example.dm_proyecto2_pedidosonline.data.entities.marvel.MarvelChars
import com.example.dm_proyecto2_pedidosonline.databinding.FragmentFirstBinding
import com.example.dm_proyecto2_pedidosonline.databinding.FragmentSecondBinding
import com.example.dm_proyecto2_pedidosonline.ui.adapters.MarvelAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
    private lateinit var binding: FragmentSecondBinding

    private lateinit var lmanager: LinearLayoutManager
    private lateinit var rvAdapter: MarvelAdapter

    private var marvelCharItems: MutableList<MarvelChars> = mutableListOf<MarvelChars>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecondBinding.inflate(layoutInflater, container, false)
        lmanager = LinearLayoutManager(
            requireActivity(),
            LinearLayoutManager.VERTICAL,
            false
        )
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onStart() {
        super.onStart()

//        arguments?.getString("busqueda")?.let { busqueda ->
//            chargeDataRv(busqueda)
//        }

        binding.buscador.setOnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val inputMethodManager =
                    requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(binding.buscador.windowToken, 0)

                chargeDataRv(textView.text.toString())

                //                val fragment = SecondFragment()
                //                val args = Bundle().apply { putString("busqueda", textView.text.toString()) }
                //                fragment.arguments = args
                //
                //                requireActivity().supportFragmentManager.beginTransaction()
                //                    .replace(R.id.frm_container, fragment)
                //                    .addToBackStack(null)
                //                    .commit()

                return@setOnEditorActionListener true
            }
            false
        }

    }

    fun chargeDataRv(search: String) {

        lifecycleScope.launch(Dispatchers.Main) {

            // lo que se ejecuta en ese contexto regresa al contexto Main
            marvelCharItems= withContext(Dispatchers.IO){
                return@withContext (MarvelLogic().getAllCharacters(search,5)
                        )
            } as MutableList<MarvelChars>
            if(marvelCharItems.size==0){
                var f= Snackbar.make(binding.buscador, "No se encontró", Snackbar.LENGTH_LONG)

                f.show()
            }
            rvAdapter.items =
                MarvelLogic().getAllCharacters(search ,5)



            // por medio del apply le decimos que debe hacer el codigo
            // funciona similar que el with
            binding.rvMarvelChars.apply {
                this.adapter = rvAdapter
                this.layoutManager = lmanager
            }

        }

    }
}
package com.example.dm_proyecto2_pedidosonline.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dm_proyecto2_pedidosonline.Logic.marvelLogic.MarvelLogic
import com.example.dm_proyecto2_pedidosonline.R
import com.example.dm_proyecto2_pedidosonline.data.entities.marvel.MarvelChars
import com.example.dm_proyecto2_pedidosonline.databinding.FragmentSecondBinding
import com.example.dm_proyecto2_pedidosonline.databinding.FragmentThirdBinding
import com.example.dm_proyecto2_pedidosonline.ui.activities.DetailsMarvelItem
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
 * Use the [ThirdFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ThirdFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var binding: FragmentThirdBinding
    private lateinit var lmanager: LinearLayoutManager
    private var marvelCharacterItems: MutableList<MarvelChars> = mutableListOf<MarvelChars>()
    private lateinit var progressBar: ProgressBar
    private  var rvAdapter: MarvelAdapter = MarvelAdapter { sendMarvelItems(it) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentThirdBinding.inflate(layoutInflater, container, false)
        // Inflate the layout for this fragment
        lmanager =LinearLayoutManager(
            requireActivity(), LinearLayoutManager.VERTICAL, false
        )
        progressBar = binding.progressBar
        return binding.root
    }
    override fun onStart() {
        super.onStart()
        binding.txtBucar.addTextChangedListener{filteredText->
            Log.d("PROBANDO",filteredText.toString())
            if(filteredText.toString().isNotEmpty()){
                reset()
                chargeDataRV(filteredText.toString())

            }
            else{
                reset()
            }
        }
    }
    fun reset(){
        marvelCharacterItems=mutableListOf<MarvelChars>()
        rvAdapter.replaceListAdapter(marvelCharacterItems)
    }
    fun sendMarvelItems(item: MarvelChars) {

        val i = Intent(requireActivity(), DetailsMarvelItem::class.java)
        i.putExtra("name", item)
        startActivity(i)
    }
    private fun chargeDataRV(nombre:String) {

        lifecycleScope.launch(Dispatchers.Main) {
            progressBar.visibility = View.VISIBLE
            marvelCharacterItems= withContext(Dispatchers.IO){
                return@withContext (MarvelLogic().getAllCharacters(nombre,5)
                        )
            } as MutableList<MarvelChars>
            if(marvelCharacterItems.size==0){
                var f= Snackbar.make(binding.txtBucar, "No se encontró", Snackbar.LENGTH_LONG)

                f.show()
            }
            rvAdapter.items =
                MarvelLogic().getAllCharacters(nombre ,5)
            binding.rvMarvel.apply{
                this.adapter = rvAdapter
                this.layoutManager = lmanager
            }
            progressBar.visibility = View.GONE
        }
    }
}
package com.example.dm_proyecto2_pedidosonline.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.dm_proyecto2_pedidosonline.R
import com.example.dm_proyecto2_pedidosonline.databinding.FragmentFirstBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dm_proyecto2_pedidosonline.Logic.jikanLogic.JikanAnimeLogic
import com.example.dm_proyecto2_pedidosonline.Logic.lists.listItems
import com.example.dm_proyecto2_pedidosonline.Logic.marvelLogic.MarvelLogic
import com.example.dm_proyecto2_pedidosonline.data.entities.marvel.MarvelChars
import com.example.dm_proyecto2_pedidosonline.ui.activities.DetailsMarvelItem
import com.example.dm_proyecto2_pedidosonline.ui.adapters.MarvelAdapter
import com.example.dm_proyecto2_pedidosonline.ui.utilities.Metodos
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A simple [Fragment] subclass.
 * Use the [FirstFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FirstFragment : Fragment() {

    private lateinit var binding: FragmentFirstBinding

    private lateinit var lmanager: LinearLayoutManager

    private lateinit var gmanager: GridLayoutManager

    private var marvelCharacterItems: MutableList<MarvelChars> = mutableListOf<MarvelChars>()

    private var rvAdapter: MarvelAdapter = MarvelAdapter { sendMarvelItems(it) }

    private val limit=99
    private var offset=0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirstBinding.inflate(
            layoutInflater,
            container,
            false
        )
        lmanager = LinearLayoutManager(
            requireActivity(),
            LinearLayoutManager.VERTICAL,
            false
        )
        gmanager = GridLayoutManager(
            requireActivity(), 2
        )
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val names = arrayListOf<String>("Spiderman", "Invisible Woman", "Eternity", "Black Widow")
        val adapter = ArrayAdapter<String>(
            requireActivity(),
            R.layout.simple_spinner,
            names
        )
        binding.spinner.adapter = adapter
        chargeDataRVInit(limit,offset)
        binding.rvSwipe.setOnRefreshListener {
            chargeDataRVInit(limit,offset)
            binding.rvSwipe.isRefreshing = false
        }
        binding.rvMarvelChars.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    if (dy > 0) {
                        val v = lmanager.childCount
                        val p = lmanager.findFirstVisibleItemPosition()
                        val t = lmanager.itemCount

                        if ((v + p) >= t) {
                            lifecycleScope.launch((Dispatchers.Main)){
                                //val items=MarvelLogic().getAllMarvelChars(0,99)
                                val newItems = with(Dispatchers.IO){
                                    MarvelLogic().getAllMarvelChars(offset ,
                                        limit)
                                }
                                rvAdapter.updateListItems(newItems)
                                this@FirstFragment.offset+=offset
                            }
                        }
                    }
                }
            })
//            binding.txtfilter.addTextChangedListener{filteredText->
//                val newItems= marvelCharacterItems.filter {
//                    items-> items.nombre.lowercase(). contains(filteredText.toString().lowercase())
//            }
//            rvAdapter.replaceListAdapter(newItems)
//        }
    }

    fun sendMarvelItems(item: MarvelChars) {
        val i = Intent(requireActivity(), DetailsMarvelItem::class.java)
        i.putExtra("name", item)
        startActivity(i);
    }

    private fun chargeDataRV(limit: Int,offset: Int) {

        if (Metodos().isOnline(requireActivity())) {
            lifecycleScope.launch(Dispatchers.Main) {
                marvelCharacterItems = withContext(Dispatchers.IO) {
                    return@withContext (MarvelLogic().getAllMarvelChars(
                        offset,
                        limit
                    ) as MutableList<MarvelChars>)
                }
                Log.d("DATOS",marvelCharacterItems.size.toString())
                rvAdapter.items = marvelCharacterItems
                //JikanAnimeLogic().getAllAnimes()

                //ListItems().returnMarvelChar()
                /*   JikanAnimeLogic().getAllAnimes()
            ) { sendMarvelItems(it) }
*/           binding.rvMarvelChars.apply {
                this.adapter = rvAdapter
                //  this.layoutManager = lmanager
                this.layoutManager = lmanager

            }
                this@FirstFragment.offset =offset+ limit
            }
        }else{
            Snackbar.make(
                binding.cardView,
                "No hay conexión",
                Snackbar.LENGTH_LONG
            ).show()
        }

    }
        private fun chargeDataRVDB() {
            lifecycleScope.launch(Dispatchers.Main) {
                marvelCharacterItems = withContext(Dispatchers.IO) {
                    var marvelCharsItems =
                        MarvelLogic().getAllCharactersDB().toMutableList()
                    if (marvelCharsItems.isEmpty()) {
                        marvelCharacterItems = (MarvelLogic().getAllCharacters(
                            name = "spider",
                            10
                        ).toMutableList())
                        MarvelLogic().insertMarvelCharstoDB(marvelCharacterItems)
                    }
                    return@withContext marvelCharsItems
                }
                rvAdapter.items = marvelCharacterItems
                //JikanAnimeLogic().getAllAnimes()
                // MarvelLogic().getAllCharactersDB()
                //ListItems().returnMarvelChar()
                /*   JikanAnimeLogic().getAllAnimes()
       ) { sendMarvelItems(it) }
    */           binding.rvMarvelChars.apply {
                this.adapter = rvAdapter
                //  this.layoutManager = lmanager
                this.layoutManager = lmanager
            }
            }
        }
    private fun chargeDataRVInit(limit:Int, offset:Int) {
        if(Metodos().isOnline(requireActivity())){
            //Mucho ojo con las corrutinas y hasta donde colococamos los {}
            lifecycleScope.launch(Dispatchers.Main) {
                marvelCharacterItems = withContext(Dispatchers.IO) {
                    return@withContext MarvelLogic().getInitChars(limit)
                }
                this@FirstFragment.offset+=limit
                rvAdapter.items = marvelCharacterItems
                binding.rvMarvelChars.apply {
                    this.adapter = rvAdapter
                    this.layoutManager = gmanager
                }
            }
        }else{
            Snackbar.make(
                binding.cardView,
                "Nohay conexion",
                Snackbar.LENGTH_LONG
            ).show()
        }
    }
    private fun chargeDataRVDB(limit: Int,offset: Int) {

        lifecycleScope.launch(Dispatchers.Main) {
            Log.d("DATOSNADA",marvelCharacterItems.size.toString())

            marvelCharacterItems = withContext(Dispatchers.IO) {
                return@withContext (MarvelLogic().getAllCharsDB(offset,limit

                ))
            }
            Log.d("DATOS",marvelCharacterItems.size.toString())

            rvAdapter.items = marvelCharacterItems

           binding.rvMarvelChars.apply {
            this.adapter = rvAdapter
            this.layoutManager = lmanager
        }
            this@FirstFragment.offset+=limit
        }

    }
    }



package com.example.dm_proyecto2_pedidosonline.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat.startActivity
import androidx.core.widget.addTextChangedListener
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.stringPreferencesKey
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
import com.example.dm_proyecto2_pedidosonline.ui.activities.dataStore
import com.example.dm_proyecto2_pedidosonline.ui.adapters.MarvelAdapter
import com.example.dm_proyecto2_pedidosonline.ui.data.UserDataStore
import com.example.dm_proyecto2_pedidosonline.ui.utilities.Metodos
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
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

    private val limit =99
    private var offset=0

    private var marvelCharacterItems: MutableList<MarvelChars> = mutableListOf()

    private lateinit var rvAdapter: MarvelAdapter
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
            //Para cambiar de horizontal a vertical
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

        lifecycleScope.launch(Dispatchers.Main) {
            getDataStore().collect {user->
                Log.d("UCE", user.email)
                Log.d("UCE", user.name)
                Log.d("UCE", user.session)
            }
        }
        val names = arrayListOf<String>("Spiderman", "Invisible Woman", "Eternity", "Black Widow")
        val adapter = ArrayAdapter<String>(
            requireActivity(),
            R.layout.simple_spinner,
            names
        )
        binding.spinner.adapter = adapter

        chargeDataRV(limit,offset)

        binding.rvSwipe.setOnRefreshListener {
//            chargeDataRV(limit,offset)
            //chargeDataRVDB(offset,limit)
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
//                            lifecycleScope.launch((Dispatchers.IO)) {
                            lifecycleScope.launch((Dispatchers.Main)) {
//                                val newItems = MarvelLogic().getAllMarvelChars(0, 99)
                                    val newItems = with(Dispatchers.IO){
                                        MarvelLogic().getAllMarvelChars(offset, limit)
                                    }
//                                withContext(Dispatchers.Main) {
//                                    rvAdapter.updateListItems(newItems)
//                                }
                                rvAdapter.updateListItems(newItems)
                                this@FirstFragment.offset+=offset
                            }
                        }
                    }
                }
            })
        binding.txtfilter.addTextChangedListener{
                filteredText->
            val newItems= marvelCharacterItems.filter {
                    items->
                items.nombre.lowercase(). contains(filteredText.toString().lowercase())
            }
            rvAdapter.replaceListAdapter(newItems)
        }
    }

    fun sendMarvelItems(item: MarvelChars) {
        val i = Intent(requireActivity(), DetailsMarvelItem::class.java)
        i.putExtra("name", item)
        startActivity(i);
    }

    fun chargeDataRV(limit: Int,offset: Int) {

        if (Metodos().isOnline(requireActivity())) {
        lifecycleScope.launch(Dispatchers.Main) {
            marvelCharacterItems = withContext(Dispatchers.IO) {
                return@withContext (
                        MarvelLogic().getAllMarvelChars(offset, limit))
//                        JikanAnimeLogic().getAllAnimes ())
            } as MutableList<MarvelChars>

            Log.d("DATOS",marvelCharacterItems.size.toString())
            rvAdapter.items = marvelCharacterItems
            binding.rvMarvelChars.apply {
                this.adapter = rvAdapter
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
                    //this.layoutManager = gmanager
                    this.layoutManager = lmanager
                }
                this@FirstFragment.offset+=limit
            }

        }
    private fun getDataStore()= requireActivity().dataStore.data.map {
            prefs->
        UserDataStore(
            name=prefs[stringPreferencesKey("usuario")].orEmpty(),
            email=prefs[stringPreferencesKey("email")].orEmpty(),
            session=prefs[stringPreferencesKey("session")].orEmpty()
        )
    }
    }



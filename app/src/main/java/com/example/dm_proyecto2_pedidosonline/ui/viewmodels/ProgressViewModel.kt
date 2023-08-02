package com.example.dm_proyecto2_pedidosonline.ui.viewmodels

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dm_proyecto2_pedidosonline.Logic.marvelLogic.MarvelLogic
import com.example.dm_proyecto2_pedidosonline.data.entities.marvel.MarvelChars
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProgressViewModel: ViewModel() {
    val progressState=MutableLiveData<Int>()
    val items = MutableLiveData<List<MarvelChars>>()
    fun processBackground(value:Long){
        viewModelScope.launch(Dispatchers.IO) {
            val state= View.VISIBLE
            progressState.postValue(state)
            delay(value)
            val state1= View.GONE
            progressState.postValue(state1)

        }
    }
    fun sumInProgressbar(value:Long){
        viewModelScope.launch(Dispatchers.IO) {
            val state= View.VISIBLE
            progressState.postValue(state)
            var total=0
            for(i in 1..300000){
                total += i
            }
            val state1=View.GONE
            progressState.postValue(state1)

        }
    }

    suspend fun getMarvelChars(offset:Int,limit:Int){
        progressState.postValue(View.VISIBLE)
        val newitems = MarvelLogic().getAllMarvelChars(offset, limit)
        items.postValue(newitems)
        progressState.postValue(View.GONE)
    }
}
package com.najwashaqilaisnan607062300125.a607062300125_najwashaqila_moproass3.Screen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.najwashaqilaisnan607062300125.a607062300125_najwashaqila_moproass3.model.Planet
import com.najwashaqilaisnan607062300125.a607062300125_najwashaqila_moproass3.network.PlanetApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.log

class MainViewModel:ViewModel() {

    var data = mutableStateOf(emptyList<Planet>())
        private set
    init {
        retrieveData()
    }

    private fun retrieveData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
               data.value=PlanetApi.service.getPlanet()
            } catch (e: Exception) {
                Log.d("MainViewModel", "Failure: ${e.message}")
            }
        }
    }
}
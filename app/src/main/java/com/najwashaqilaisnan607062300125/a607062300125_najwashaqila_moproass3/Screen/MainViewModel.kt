package com.najwashaqilaisnan607062300125.a607062300125_najwashaqila_moproass3.Screen


import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.najwashaqilaisnan607062300125.a607062300125_najwashaqila_moproass3.model.Planet
import com.najwashaqilaisnan607062300125.a607062300125_najwashaqila_moproass3.network.ApiStatus
import com.najwashaqilaisnan607062300125.a607062300125_najwashaqila_moproass3.network.PlanetApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream

class MainViewModel : ViewModel() {
    private val _data = mutableStateOf<List<Planet>>(emptyList())
    val data: State<List<Planet>> = _data

    private val _status = mutableStateOf(ApiStatus.LOADING)
    val status: State<ApiStatus> = _status

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    private val _planetToEdit = mutableStateOf<Planet?>(null)
    val planetToEdit: State<Planet?> = _planetToEdit

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    fun retrievePlanets() {
        viewModelScope.launch(Dispatchers.IO) {
            _status.value = ApiStatus.LOADING
            try {
                val result = PlanetApi.service.getAllPlanets()
                if (result.success) {
                    _data.value = result.data
                    _status.value = ApiStatus.SUCCESS
                } else {
                    throw Exception("Failed to retrieve planet data")
                }
            } catch (e: Exception) {
                Log.d("MainViewModel", "Failure: ${e.message}")
                _status.value = ApiStatus.FAILED
                _errorMessage.value = "Error: ${e.message}"
            }
        }
    }

    fun createPlanet(name: String, description: String, bitmap: Bitmap) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            try {
                val result = PlanetApi.service.createPlanet(
                    name.toRequestBody("text/plain".toMediaTypeOrNull()),
                    description.toRequestBody("text/plain".toMediaTypeOrNull()),
                    bitmap.toMultipartBody()
                )
                if (result.success) retrievePlanets()
                else throw Exception("Failed to create planet")
            } catch (e: Exception) {
                _errorMessage.value = "Error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updatePlanet(id: Int, name: String, description: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            try {
                val body = mapOf("name" to name, "description" to description)
                val result = PlanetApi.service.updatePlanet(id, body)
                if (result.success) {
                    retrievePlanets()
                } else {
                    throw Exception("Failed to update planet")
                }
            } catch (e: Exception) {
                Log.d("MainViewModel", "Update failed: ${e.message}")
                _errorMessage.value = "Error updating: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deletePlanet(planetId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            try {
                val result = PlanetApi.service.deletePlanet(planetId)
                if (result.success) {
                    retrievePlanets()
                } else {
                    throw Exception(result.message)
                }
            } catch (e: Exception) {
                Log.d("MainViewModel", "Delete failed: ${e.message}")
                _errorMessage.value = "Error deleting: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun onEditClick(planet: Planet) {
        _planetToEdit.value = planet
    }

    fun onDialogDismiss() {
        _planetToEdit.value = null
    }

    private fun Bitmap.toMultipartBody(): MultipartBody.Part {
        val stream = ByteArrayOutputStream()
        compress(Bitmap.CompressFormat.JPEG, 80, stream)
        val byteArray = stream.toByteArray()
        val requestBody = byteArray.toRequestBody(
            "image/jpeg".toMediaTypeOrNull(), 0, byteArray.size)
        return MultipartBody.Part.createFormData("image", "image.jpg", requestBody)
    }

    fun clearMessage() {
        _errorMessage.value = null
    }
}
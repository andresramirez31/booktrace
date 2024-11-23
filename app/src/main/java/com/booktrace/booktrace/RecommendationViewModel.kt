package com.booktrace.booktrace

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecommendationViewModel : ViewModel() {
    private val _recommendations = mutableStateOf<List<Recommendation>>(emptyList())
    val recommendations: State<List<Recommendation>> = _recommendations

    fun fetchRecommendations(userId: Int, numRecommendations: Int = 4) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.getRecommendation(userId, numRecommendations).execute()
                if (response.isSuccessful) {
                    _recommendations.value = response.body() ?: emptyList()
                } else {
                    // Manejar error
                    _recommendations.value = emptyList()
                }
            } catch (e: Exception) {
                // Manejar fallo en la llamada
                e.printStackTrace()
                Log.e("RecommendationViewModel", "Error fetching recommendations: ${e.message}")
                _recommendations.value = emptyList()
            }
        }
    }
}
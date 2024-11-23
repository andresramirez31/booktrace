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
                RetrofitClient.instance.getRecommendation(userId, numRecommendations)
                    .enqueue(object : Callback<List<List<Recommendation>>> {
                        override fun onResponse(
                            call: Call<List<List<Recommendation>>>,
                            response: Response<List<List<Recommendation>>>
                        ) {
                            if (response.isSuccessful) {
                                _recommendations.value = response.body()?.flatten() ?: emptyList()
                            } else {
                                Log.e("RecommendationViewModel", "Error: ${response.code()}")
                                _recommendations.value = emptyList()
                            }
                        }

                        override fun onFailure(call: Call<List<List<Recommendation>>>, t: Throwable) {
                            Log.e("RecommendationViewModel", "Failure: ${t.message}")
                            _recommendations.value = emptyList()
                        }
                    })
            } catch (e: Exception) {
                Log.e("RecommendationViewModel", "Error: ${e.message}")
                _recommendations.value = emptyList()
            }
        }
    }
}
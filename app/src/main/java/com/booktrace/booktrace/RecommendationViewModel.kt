package com.booktrace.booktrace

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecommendationViewModel : ViewModel() {

    var recommendations: List<Recommendation> = emptyList()
        private set

    fun fetchRecommendations(userId: Int, numRecommendations: Int = 4) {
        viewModelScope.launch {
            RetrofitClient.instance.getRecommendation(userId, numRecommendations).enqueue(object : Callback<List<Recommendation>> {
                override fun onResponse(call: Call<List<Recommendation>>, response: Response<List<Recommendation>>) {
                    if (response.isSuccessful) {
                        recommendations = response.body() ?: emptyList()
                    } else {
                        // Manejar error
                    }
                }

                override fun onFailure(call: Call<List<Recommendation>>, t: Throwable) {
                    // Manejar error
                }
            })
        }
    }
}
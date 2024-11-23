package com.booktrace.booktrace

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

data class Recommendation(
    val id: Int,
    val isbn: String,
    val authors: String,
    val title: String,
    val original_title: String
)

interface ApiService {
    @GET("recommend")
    fun getRecommendation(
        @Query("user_id") userId: Int,
        @Query("num_recommendations") numRecommendations: Int = 4): Call<List<Recommendation>>
}
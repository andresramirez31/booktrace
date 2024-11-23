package com.booktrace.booktrace.models

data class Recommendation(
    val id: Int,
    val isbn: String,
    val authors: String,
    val title: String,
    val original_title: String
)
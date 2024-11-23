package com.booktrace.booktrace.ui.model

data class Recommendation(
    val id: Int,
    val isbn: String,
    val authors: String,
    val title: String,
    val original_title: String
)
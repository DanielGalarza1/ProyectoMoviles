package uta.fisei.practica_kotlin_movie.model

import com.google.gson.annotations.SerializedName

data class MovieDbResult(
    val page: Int,
    val results: List<MovieDb>,
    val total_pages: Int,
    @SerializedName("total_results")
    val totalresults: Int
)
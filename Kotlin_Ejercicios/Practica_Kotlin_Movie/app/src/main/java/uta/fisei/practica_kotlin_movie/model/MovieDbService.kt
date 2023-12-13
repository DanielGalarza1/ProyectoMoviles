package uta.fisei.practica_kotlin_movie.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieDbService {
    @GET("popular")
    fun listPopularMovies(@Query("apiKey") apiKey: String) : Call<MovieDbResult>
}
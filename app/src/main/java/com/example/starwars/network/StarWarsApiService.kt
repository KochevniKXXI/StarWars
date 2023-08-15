package com.example.starwars.network

import com.example.starwars.data.model.Film
import com.example.starwars.data.model.PageResources
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface StarWarsApiService {
    @GET("{type}")
    suspend fun getPageResources(@Path("type") type: String, @Query("search") search: String, @Query("page") page: Int): PageResources

    @GET("{path}")
    suspend fun getFilms(@Path("path") path: String): Film
}

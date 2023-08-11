package com.example.starwars.network

import retrofit2.http.GET
import retrofit2.http.Query

interface StarWarsApiService {
    @GET("starships/")
    suspend fun getPageStarships(@Query("search") search: String, @Query("page") page: Int): PageStarships
}

package com.example.starwars.data

import com.example.starwars.network.StarWarsApiService
import com.example.starwars.network.resources.Starship
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface StarWarsRepository {
    fun getStarshipsStream(search: String): Flow<List<Starship>>
}

class NetworkStarWarsRepository @Inject constructor(
    private val starWarsApiService: StarWarsApiService
) : StarWarsRepository {
    override fun getStarshipsStream(search: String): Flow<List<Starship>> =
        flow {
            var page = 1
            val listStarship = mutableListOf<Starship>()
            do {
                val request = starWarsApiService.getPageStarships(search, page)
                listStarship.addAll(request.results)
                page++
            } while (request.next != null)
            emit(listStarship)
        }
}
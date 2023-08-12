package com.example.starwars.data

import com.example.starwars.network.StarWarsApiService
import com.example.starwars.network.resources.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

enum class Resources {
    PEOPLE,
    STARSHIPS,
    PLANETS
}

interface StarWarsRepository {
    fun getResourcesStream(search: String): Flow<List<Resource>>
}

class NetworkStarWarsRepository @Inject constructor(
    private val starWarsApiService: StarWarsApiService
) : StarWarsRepository {
    override fun getResourcesStream(search: String): Flow<List<Resource>> =
        flow {
            val listStarship = mutableListOf<Resource>()

            Resources.entries.forEach { resource ->
                var page = 1
                do {
                    val request = starWarsApiService.getPageResources(resource.name.lowercase(), search, page)
                    listStarship.addAll(request.results)
                    page++
                } while (request.next != null)
            }
            emit(listStarship)
        }
}
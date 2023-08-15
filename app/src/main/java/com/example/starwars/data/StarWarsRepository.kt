package com.example.starwars.data

import com.example.starwars.data.model.Film
import com.example.starwars.network.StarWarsApiService
import com.example.starwars.data.model.Resource
import com.example.starwars.di.BASE_URL
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
    fun getFilmsStream(listUrl: Set<String>): Flow<List<Film>>
}

class NetworkStarWarsRepository @Inject constructor(
    private val starWarsApiService: StarWarsApiService
) : StarWarsRepository {
    override fun getResourcesStream(search: String): Flow<List<Resource>> =
        flow {
            val listResources = mutableListOf<Resource>()

            Resources.entries.forEach { resource ->
                var page = 1
                do {
                    val request = starWarsApiService.getPageResources(resource.name.lowercase(), search, page)
                    listResources.addAll(request.results)
                    page++
                } while (request.next != null)
            }
            emit(listResources)
        }

    override fun getFilmsStream(listUrl: Set<String>): Flow<List<Film>> =
        flow {
            val listResources = listUrl.map { url ->
                starWarsApiService.getFilms(url.removePrefix(BASE_URL))
            }
            emit(listResources)
        }
}
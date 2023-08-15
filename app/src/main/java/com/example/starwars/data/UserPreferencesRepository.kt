package com.example.starwars.data

import android.util.Log
import androidx.datastore.core.DataStore
import com.example.starwars.data.model.Film
import com.example.starwars.data.model.Hero
import com.example.starwars.data.model.Planet
import com.example.starwars.data.model.Resource
import com.example.starwars.data.model.Starship
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import java.io.IOException
import javax.inject.Inject

class UserPreferencesRepository @Inject constructor(
    private val dataStore: DataStore<Set<Resource>>,
    private val starWarsRepository: StarWarsRepository
) {

    private companion object {
        const val TAG = "UserPreferencesRepository"
    }

    val userFavoritesFlow: Flow<Set<Resource>> = dataStore.data
        .catch {
            if (it is IOException) {
                Log.e(TAG, "Ошибка чтения избранного.", it)
                emit(setOf())
            } else {
                throw it
            }
        }

    suspend fun changeFavoritesResources(resource: Resource) {
        dataStore.updateData { oldSetResources ->
            buildSet {
                addAll(oldSetResources)
                if (!add(resource)) removeResource(resource)
            }
        }
    }

    suspend fun removeResourceFromFavorites(resource: Resource) {
        dataStore.updateData { oldSetResources ->
            buildSet {
                addAll(oldSetResources)
                removeResource(resource)
            }
        }
    }

    private fun MutableSet<Resource>.removeResource(resource: Resource) {
        remove(resource)
        removeIf {
            it is Film && !any { res ->
                when (res) {
                    is Hero -> res.films.any { film -> film == it.url }
                    is Starship -> res.films.any { film -> film == it.url }
                    is Planet -> res.films.any { film -> film == it.url }
                    else -> false
                }
            }
        }
    }

    suspend fun addFilmsByUrl(listUrl: Set<String>) {
        dataStore.updateData { oldSetResources ->
            buildSet {
                addAll(oldSetResources)
                addAll(starWarsRepository.getFilmsStream(listUrl).first())
            }
        }
    }
}
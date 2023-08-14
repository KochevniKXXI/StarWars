package com.example.starwars.data

import android.util.Log
import androidx.datastore.core.DataStore
import com.example.starwars.data.model.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import java.io.IOException
import javax.inject.Inject

class UserPreferencesRepository @Inject constructor(
    private val dataStore: DataStore<List<Resource>>,
) {

    private companion object {
        const val TAG = "UserPreferencesRepository"
    }

    val userFavoritesFlow: Flow<List<Resource>> = dataStore.data
        .catch {
            if (it is IOException) {
                Log.e(TAG, "Ошибка чтения избранного.", it)
                emit(listOf())
            } else {
                throw it
            }
        }

    suspend fun changeFavoritesResource(resource: Resource) {
        if (userFavoritesFlow.first().contains(resource)) {
            removeResource(resource)
        } else {
            dataStore.updateData {
                buildList {
                    addAll(it)
                    add(resource)
                }
            }
        }
    }

    suspend fun removeResource(resource: Resource) {
        dataStore.updateData {
            buildList {
                addAll(it)
                remove(resource)
            }
        }
    }
}
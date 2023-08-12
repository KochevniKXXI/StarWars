package com.example.starwars.data

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class UserPreferencesRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) {

    private companion object {
        const val TAG = "UserPreferencesRepository"
    }

    fun isFavorite(resourceKey: String) = dataStore.data
        .catch {
            if (it is IOException) {
                Log.e(TAG, "Ошибка чтения избранного.", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[booleanPreferencesKey(resourceKey)]
        }

    suspend fun updateFavoritesResources(resourceKey: String) {
        dataStore.edit { preferences ->
            if (preferences[booleanPreferencesKey(resourceKey)] == null) {
                preferences[booleanPreferencesKey(resourceKey)] = true
            } else {
                preferences.remove(booleanPreferencesKey(resourceKey))
            }
        }
    }
}
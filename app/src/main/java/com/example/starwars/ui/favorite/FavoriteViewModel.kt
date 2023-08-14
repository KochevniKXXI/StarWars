package com.example.starwars.ui.favorite

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwars.data.StarWarsRepository
import com.example.starwars.data.UserPreferencesRepository
import com.example.starwars.ui.search.ResourceDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val starWarsRepository: StarWarsRepository
) : ViewModel() {
    var favoriteUiState: FavoriteUiState by mutableStateOf(FavoriteUiState())
        private set

    init {
        getFavorites()
    }

    private fun getFavorites() {
        viewModelScope.launch {
            favoriteUiState = FavoriteUiState(
                userPreferencesRepository.userFavoritesFlow.first().map {
                    it.toResourceDetails()
                }
            )
        }
    }

    fun removeResourceFromFavorites(resource: ResourceDetails) {
        viewModelScope.launch {
            userPreferencesRepository.removeResource(resource.toResource())
        }
        val resources = favoriteUiState.resources.toMutableList().apply {
            remove(resource)
        }
        favoriteUiState = FavoriteUiState(resources)
    }
}

data class FavoriteUiState(
    val resources: List<ResourceDetails> = listOf()
)
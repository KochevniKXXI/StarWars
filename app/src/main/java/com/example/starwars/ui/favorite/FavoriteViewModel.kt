package com.example.starwars.ui.favorite

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwars.data.UserPreferencesRepository
import com.example.starwars.ui.search.HeroDetails
import com.example.starwars.ui.search.PlanetDetails
import com.example.starwars.ui.search.ResourceDetails
import com.example.starwars.ui.search.StarshipDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
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
            val relatedFilms = favoriteUiState.resources.fold(mutableSetOf<String>()) { acc, resource ->
                when (resource) {
                    is HeroDetails -> {
                        acc.addAll(resource.films.filterNot { url -> favoriteUiState.resources.any { it.url == url } })
                        acc
                    }
                    is StarshipDetails -> {
                        acc.addAll(resource.films.filterNot { url -> favoriteUiState.resources.any { it.url == url } })
                        acc
                    }
                    is PlanetDetails -> {
                        acc.addAll(resource.films.filterNot { url -> favoriteUiState.resources.any { it.url == url } })
                        acc
                    }
                    else -> acc
                }
            }.toSet()
            if (relatedFilms.isNotEmpty()) {
                userPreferencesRepository.addFilmsByUrl(relatedFilms)
                favoriteUiState = FavoriteUiState(
                    userPreferencesRepository.userFavoritesFlow.first().map {
                        it.toResourceDetails()
                    }
                )
            }
        }
    }

    fun removeResourceFromFavorites(resource: ResourceDetails) {
        viewModelScope.launch {
            userPreferencesRepository.removeResourceFromFavorites(resource.toResource())
            getFavorites()
        }
    }
}

data class FavoriteUiState(
    val resources: List<ResourceDetails> = listOf()
)
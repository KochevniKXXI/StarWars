package com.example.starwars.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwars.data.StarWarsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

sealed interface HomeUiState {
    data object StartSearch : HomeUiState
    data class Success(val starships: List<ResourceDetails>) : HomeUiState
    data object Error : HomeUiState
    data object Loading : HomeUiState
}

@HiltViewModel
class HomeViewModel @Inject constructor(private val starWarsRepository: StarWarsRepository) :
    ViewModel() {
    var homeUiState: HomeUiState by mutableStateOf(HomeUiState.StartSearch)
        private set

    fun getStarshipByName(search: String) {
        if (search.length < 2) {
            homeUiState = HomeUiState.StartSearch
        } else {
            homeUiState = HomeUiState.Loading
            viewModelScope.launch {
                homeUiState = try {
                    HomeUiState.Success(
                        starWarsRepository.getResourcesStream(search)
                            .filterNotNull()
                            .first()
                            .map { resource ->
                                resource.toResourceDetails()
                            }
                    )
                } catch (e: IOException) {
                    HomeUiState.Error
                }
            }
        }
    }
}

interface ResourceDetails

data class StarshipDetails(
    val name: String,
    val model: String,
    val manufacturer: String,
    val passengers: Int?
) : ResourceDetails

data class HeroDetails(
    val name: String,
    val gender: String,
    val numberStarships: Int
) : ResourceDetails

data class PlanetDetails(
    val name: String,
    val diameter: Int?,
    val population: Long?
) : ResourceDetails

data class FilmDetails(
    val title: String,
    val director: String,
    val producer: String
) : ResourceDetails
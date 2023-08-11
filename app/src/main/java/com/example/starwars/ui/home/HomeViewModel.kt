package com.example.starwars.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwars.data.StarWarsRepository
import com.example.starwars.network.resources.Starship
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

sealed interface HomeUiState {
    data object StartSearch : HomeUiState
    data class Success(val starships: List<StarshipDetails>) : HomeUiState
    data object Error : HomeUiState
    data object Loading : HomeUiState
}

@HiltViewModel
class HomeViewModel @Inject constructor(private val starWarsRepository: StarWarsRepository) :
    ViewModel() {
    var homeUiState: HomeUiState by mutableStateOf(HomeUiState.StartSearch)
        private set

    fun getStarshipByName(search: String) {
        homeUiState = HomeUiState.Loading
        viewModelScope.launch {
            homeUiState = try {
                HomeUiState.Success(
                    starWarsRepository.getStarshipsStream(search)
                        .filterNotNull()
                        .first()
                        .map { starship ->
                            starship.toStarshipDetails()
                        }
                )
            } catch (e: IOException) {
                HomeUiState.Error
            }
        }
    }
}

data class StarshipDetails(
    val name: String,
    val model: String,
    val manufacturer: String,
    val passengers: Int?
)

fun Starship.toStarshipDetails() = StarshipDetails(
    name = name,
    model = model,
    manufacturer = manufacturer,
    passengers = passengers.toIntOrNull()
)
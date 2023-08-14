package com.example.starwars.ui.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwars.data.StarWarsRepository
import com.example.starwars.data.UserPreferencesRepository
import com.example.starwars.data.model.Film
import com.example.starwars.data.model.Hero
import com.example.starwars.data.model.Planet
import com.example.starwars.data.model.Resource
import com.example.starwars.data.model.Starship
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

sealed interface HomeUiState {
    data object StartSearch : HomeUiState
    data class Success(val resources: List<ResourceDetails>) : HomeUiState
    data object Error : HomeUiState
    data object Loading : HomeUiState
}

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val starWarsRepository: StarWarsRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    var homeUiState: HomeUiState by mutableStateOf(HomeUiState.StartSearch)
        private set

    suspend fun findResourcesByName(search: String) {
        if (search.length < 2) {
            homeUiState = HomeUiState.StartSearch
        } else {
            homeUiState = HomeUiState.Loading
            homeUiState = try {
                HomeUiState.Success(
                    starWarsRepository.getResourcesStream(search)
                        .filterNotNull()
                        .first()
                        .map { resource ->
                            resource.toResourceDetails().apply {
                                isFavorite = userPreferencesRepository.userFavoritesFlow.first().contains(resource)
                            }
                        }
                )
            } catch (e: IOException) {
                HomeUiState.Error
            }
        }
    }

    fun changeFavoritesResources(resourceDetails: ResourceDetails) {
        viewModelScope.launch {
            userPreferencesRepository.changeFavoritesResource(resourceDetails.toResource())
        }
        val resources = (homeUiState as HomeUiState.Success).resources.toMutableList().apply {
            set(
                indexOfFirst { it.url == resourceDetails.url },
                resourceDetails.copy(isFavorite = !resourceDetails.isFavorite)
            )
        }
        homeUiState = HomeUiState.Success(resources)
    }
}

interface ResourceDetails {
    val url: String
    var isFavorite: Boolean

    fun copy(isFavorite: Boolean = this.isFavorite): ResourceDetails
    fun toResource(): Resource

    companion object : ResourceDetails {
        override val url = ""
        override var isFavorite = false
        override fun copy(isFavorite: Boolean): ResourceDetails = this
        override fun toResource(): Resource = Resource
    }
}

data class StarshipDetails(
    val name: String,
    val model: String,
    val manufacturer: String,
    val passengers: Int?,
    override val url: String,
    override var isFavorite: Boolean = false
) : ResourceDetails {
    override fun copy(isFavorite: Boolean) = StarshipDetails(
        name = name,
        model = model,
        manufacturer = manufacturer,
        passengers = passengers,
        url = url,
        isFavorite = isFavorite
    )

    override fun toResource(): Resource = Starship(
        name =  name,
        model = model,
        manufacturer = manufacturer,
        passengers = passengers.toString(),
        url = url
    )
}

data class HeroDetails(
    val name: String,
    val gender: String,
    val starships: List<String>,
    override val url: String,
    override var isFavorite: Boolean = false
) : ResourceDetails {
    override fun copy(isFavorite: Boolean) = HeroDetails(
        name = name,
        gender = gender,
        starships = starships,
        url = url,
        isFavorite = isFavorite
    )

    override fun toResource() = Hero(
        name = name,
        gender =gender,
        starships = starships,
        url = url
    )
}

data class PlanetDetails(
    val name: String,
    val diameter: Int?,
    val population: Long?,
    override val url: String,
    override var isFavorite: Boolean = false
) : ResourceDetails {
    override fun copy(isFavorite: Boolean) = PlanetDetails(
        name = name,
        diameter = diameter,
        population = population,
        url = url,
        isFavorite = isFavorite
    )

    override fun toResource() = Planet(
        name = name,
        diameter = diameter.toString(),
        population = population.toString(),
        url = url
    )
}

data class FilmDetails(
    val title: String,
    val director: String,
    val producer: String,
    override val url: String,
    override var isFavorite: Boolean = false
) : ResourceDetails {
    override fun copy(isFavorite: Boolean) = FilmDetails(
        title = title,
        director = director,
        producer = producer,
        url = url,
        isFavorite = isFavorite
    )

    override fun toResource() = Film(
        title = title,
        director = director,
        producer = producer,
        url = url
    )
}
package com.example.starwars.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.starwars.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {

    var query by rememberSaveable { mutableStateOf("") }
    var active by rememberSaveable { mutableStateOf(false) }

    Box(modifier = modifier.fillMaxSize()) {
        SearchBar(
            modifier = Modifier.align(Alignment.TopCenter),
            query = query,
            onQueryChange = {
                query = it
                viewModel.getStarshipByName(query)
            },
            onSearch = { active = false },
            active = active,
            onActiveChange = { active = it },
            placeholder = {
                Text(
                    text = "${
                        stringResource(id = R.string.heroes)
                    }, ${
                        stringResource(id = R.string.starships).lowercase()
                    }, ${
                        stringResource(id = R.string.planets).lowercase()
                    }"
                )
            },
            leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null) }
        ) {
            when (val homeUiState = viewModel.homeUiState) {
                is HomeUiState.StartSearch -> StartSearchBox()
                is HomeUiState.Loading -> LoadingBox(modifier = modifier.fillMaxSize())
                is HomeUiState.Success -> ResultBox(
                    resources = homeUiState.starships,
                    modifier = modifier.fillMaxSize()
                )

                is HomeUiState.Error -> ErrorBox(modifier = modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun ErrorBox(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
    }
}

@Composable
fun LoadingBox(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun ResultBox(
    resources: List<ResourceDetails>,
    modifier: Modifier = Modifier
) {
    if (resources.isEmpty()) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = stringResource(R.string.no_results))
        }
    } else {
        LazyColumn(modifier = modifier) {
            items(resources) { resource ->
                var isFavorites by rememberSaveable { mutableStateOf(false) }

                ListItem(
                    headlineContent = {
                        Text(
                            text = when (resource) {
                                is StarshipDetails -> resource.name
                                is HeroDetails -> resource.name
                                is PlanetDetails -> resource.name
                                else -> ""
                            }
                        )
                    },
                    supportingContent = { ResourceSupportingContent(resource) },
                    trailingContent = {
                        IconButton(onClick = { isFavorites = !isFavorites }) {
                            Icon(
                                imageVector = if (isFavorites) Icons.Filled.Favorite
                                else Icons.Outlined.FavoriteBorder,
                                contentDescription = null
                            )
                        }
                    }
                )
                Divider()
            }
        }
    }
}

@Composable
private fun ResourceSupportingContent(
    resource: ResourceDetails,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        when (resource) {
            is StarshipDetails -> {
                Text(text = stringResource(id = R.string.model, resource.model))
                Text(text = stringResource(id = R.string.manufacturer, resource.manufacturer))
                Text(
                    text = stringResource(
                        id = R.string.capacity,
                        if (resource.passengers == null) {
                            stringResource(id = R.string.not_available)
                        } else {
                            pluralStringResource(
                                id = R.plurals.capacity,
                                count = resource.passengers,
                                resource.passengers
                            )
                        }
                    )
                )
            }

            is HeroDetails -> {
                Text(text = stringResource(id = R.string.gender, resource.gender))
                Text(
                    text = stringResource(
                        id = R.string.number_starships,
                        pluralStringResource(
                            id = R.plurals.number_starships,
                            count = resource.numberStarships,
                            resource.numberStarships
                        )
                    )
                )
            }

            is PlanetDetails -> {
                Text(
                    text = stringResource(
                        id = R.string.diameter,
                        if (resource.diameter == null) {
                            stringResource(id = R.string.unknown)
                        } else {
                            pluralStringResource(
                                id = R.plurals.diameter,
                                count = resource.diameter,
                                resource.diameter
                            )
                        }
                    )
                )
                Text(
                    text = stringResource(
                        id = R.string.population,
                        if (resource.population == null) {
                            stringResource(id = R.string.unknown)
                        } else {
                            pluralStringResource(
                                id = R.plurals.population,
                                count = resource.population.toString().takeLast(2).toInt(),
                                resource.population
                            )
                        }
                    )
                )
            }

            else -> {}
        }
    }
}

@Composable
fun StartSearchBox(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = stringResource(R.string.start_search))
    }
}
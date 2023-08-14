package com.example.starwars.ui.favorite

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.starwars.R
import com.example.starwars.ui.navigation.NavigationDestination
import com.example.starwars.ui.search.HeroDetails
import com.example.starwars.ui.search.ResourceDetails
import com.example.starwars.ui.search.ResourceListItem
import com.example.starwars.ui.search.StarshipDetails

object FavoriteDestination : NavigationDestination {
    override val route = "favorite_screen"
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoriteViewModel = hiltViewModel()
) {
    val favoriteUiState = viewModel.favoriteUiState
    if (favoriteUiState.resources.isEmpty()) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = stringResource(id = R.string.no_favorites))
        }
    } else {
        Column(
            modifier = modifier.padding(
                horizontal = dimensionResource(id = R.dimen.medium_padding),
                vertical = dimensionResource(id = R.dimen.small_padding)
            ),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.medium_padding))
        ) {
            favoriteUiState.resources.groupBy {
                it::class
            }.forEach { mapResources ->
                Column {
                    Text(
                        text = when (mapResources.key) {
                            HeroDetails::class -> stringResource(id = R.string.heroes)
                            StarshipDetails::class -> stringResource(id = R.string.starships)
                            else -> stringResource(id = R.string.planets)
                        },
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.small_spacing)))
                    val pagerState = rememberPagerState {
                        mapResources.value.size
                    }
                    HorizontalPager(
                        state = pagerState,
                        contentPadding = if (pagerState.pageCount > 1) when (pagerState.currentPage) {
                            0 -> PaddingValues(end = dimensionResource(id = R.dimen.large_padding))
                            pagerState.pageCount - 1 -> PaddingValues(start = dimensionResource(id = R.dimen.large_padding))
                            else -> PaddingValues(horizontal = dimensionResource(id = R.dimen.large_padding))
                        }
                        else PaddingValues(dimensionResource(id = R.dimen.no_padding)),
                        pageSpacing = dimensionResource(id = R.dimen.small_spacing),
                        userScrollEnabled = pagerState.pageCount != 1
                    ) { indexResource ->
                        CardResource(
                            resource = mapResources.value[indexResource],
                            onDeleteClick = { viewModel.removeResourceFromFavorites(mapResources.value[indexResource]) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CardResource(
    resource: ResourceDetails,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.height(dimensionResource(id = R.dimen.card_height)),
        shape = MaterialTheme.shapes.extraLarge
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            ResourceListItem(
                resource = resource,
                colors = ListItemDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            )
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomEnd
            ) {
                IconButton(
                    onClick = onDeleteClick
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = stringResource(id = R.string.delete_from_favorites)
                    )
                }
            }
        }
    }
}
package com.example.starwars.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.starwars.ui.favorite.FavoriteDestination
import com.example.starwars.ui.favorite.FavoriteScreen
import com.example.starwars.ui.search.SearchDestination
import com.example.starwars.ui.search.SearchScreen

@Composable
fun StarWarsNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = SearchDestination.route,
        modifier = modifier
    ) {
        composable(route = SearchDestination.route) {
            SearchScreen()
        }
        composable(route = FavoriteDestination.route) {
            FavoriteScreen()
        }
    }
}
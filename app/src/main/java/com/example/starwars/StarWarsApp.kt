package com.example.starwars

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.starwars.ui.favorite.FavoriteDestination
import com.example.starwars.ui.navigation.StarWarsNavHost
import com.example.starwars.ui.search.SearchDestination

@Composable
fun StarWarsApp(navController: NavHostController = rememberNavController()) {
    Scaffold(
        bottomBar = {
            var selectedItem by remember { mutableIntStateOf(0) }
            NavigationBar {
                stringArrayResource(id = R.array.bottom_bar_items).forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItem == index,
                        onClick = {
                            if (selectedItem != index)
                                selectedItem = index
                            navController.navigate(
                                when (index) {
                                    0 -> SearchDestination.route
                                    else -> FavoriteDestination.route
                                }
                            )
                        },
                        icon = {
                            Icon(
                                imageVector = when (index) {
                                    0 -> Icons.Default.Search
                                    else -> Icons.Default.Favorite
                                },
                                contentDescription = item
                            )
                        },
                        label = { Text(item) }
                    )
                }
            }
        }
    ) { innerPadding ->
        StarWarsNavHost(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

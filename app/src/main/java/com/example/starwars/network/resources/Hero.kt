package com.example.starwars.network.resources

import kotlinx.serialization.Serializable

@Serializable
data class Hero(
    val name: String,
    val gender: String,
    val starships: List<Starship>
)

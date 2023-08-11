package com.example.starwars.network

import com.example.starwars.network.resources.Starship
import kotlinx.serialization.Serializable

@Serializable
data class PageStarships(
    val count: Int,
    val next: String?,
    val results: List<Starship>
)

package com.example.starwars.network.resources

import kotlinx.serialization.Serializable

@Serializable
data class Film(
    val title: String,
    val director: String,
    val producer: String
)

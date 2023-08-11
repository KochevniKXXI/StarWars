package com.example.starwars.network.resources

import kotlinx.serialization.Serializable

@Serializable
data class Starship(
    val name: String,
    val model: String,
    val manufacturer: String,
    val passengers: String
)

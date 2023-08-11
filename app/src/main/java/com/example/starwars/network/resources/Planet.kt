package com.example.starwars.network.resources

import kotlinx.serialization.Serializable

@Serializable
data class Planet(
    val name: String,
    val diameter: String,
    val population: String
)

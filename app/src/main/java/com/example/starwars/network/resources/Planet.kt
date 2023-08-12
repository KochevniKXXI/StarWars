package com.example.starwars.network.resources

import com.example.starwars.ui.home.PlanetDetails
import kotlinx.serialization.Serializable

@Serializable
data class Planet(
    val name: String,
    val diameter: String,
    val population: String
) : Resource {
    override fun toResourceDetails() = PlanetDetails(
        name = name,
        diameter = diameter.toIntOrNull(),
        population = population.toLongOrNull()
    )
}

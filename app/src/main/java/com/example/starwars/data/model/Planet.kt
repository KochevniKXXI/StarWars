package com.example.starwars.data.model

import com.example.starwars.ui.search.PlanetDetails
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("planet")
data class Planet(
    val name: String,
    val diameter: String,
    val population: String,
    override val url: String
) : Resource {
    override fun toResourceDetails() = PlanetDetails(
        name = name,
        diameter = diameter.toIntOrNull(),
        population = population.toLongOrNull(),
        url = url
    )
}

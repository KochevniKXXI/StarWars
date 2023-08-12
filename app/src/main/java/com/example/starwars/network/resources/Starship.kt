package com.example.starwars.network.resources

import com.example.starwars.ui.home.StarshipDetails
import kotlinx.serialization.Serializable

@Serializable
data class Starship(
    val name: String,
    val model: String,
    val manufacturer: String,
    val passengers: String,
    override val url: String
) : Resource {
    override fun toResourceDetails() = StarshipDetails(
        name = name,
        model = model,
        manufacturer = manufacturer,
        passengers = passengers.toIntOrNull(),
        url = url
    )
}
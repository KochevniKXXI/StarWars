package com.example.starwars.data.model

import com.example.starwars.ui.search.StarshipDetails
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("starship")
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
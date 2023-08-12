package com.example.starwars.network.resources

import com.example.starwars.ui.home.FilmDetails
import kotlinx.serialization.Serializable

@Serializable
data class Film(
    val title: String,
    val director: String,
    val producer: String
) : Resource {
    override fun toResourceDetails() = FilmDetails(
        title = title,
        director = director,
        producer = producer
    )
}

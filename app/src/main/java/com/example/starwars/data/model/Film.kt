package com.example.starwars.data.model

import com.example.starwars.ui.search.FilmDetails
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("film")
data class Film(
    val title: String,
    val director: String,
    val producer: String,
    override val url: String
) : Resource {
    override fun toResourceDetails() = FilmDetails(
        title = title,
        director = director,
        producer = producer,
        url = url
    )
}

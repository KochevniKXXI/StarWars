package com.example.starwars.data.model

import com.example.starwars.ui.search.HeroDetails
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("hero")
data class Hero(
    val name: String,
    val gender: String,
    val starships: List<String>,
    override val url: String
) : Resource {
    override fun toResourceDetails() = HeroDetails(
        name = name,
        gender = gender,
        starships = starships,
        url = url
    )
}

package com.example.starwars.network.resources

import com.example.starwars.ui.home.HeroDetails
import kotlinx.serialization.Serializable

@Serializable
data class Hero(
    val name: String,
    val gender: String,
    val starships: List<String>
) : Resource {
    override fun toResourceDetails() = HeroDetails(
        name = name,
        gender = gender,
        numberStarships = starships.size
    )
}

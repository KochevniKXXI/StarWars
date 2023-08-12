package com.example.starwars.network

import com.example.starwars.network.resources.Film
import com.example.starwars.network.resources.Hero
import com.example.starwars.network.resources.Planet
import com.example.starwars.network.resources.Resource
import com.example.starwars.network.resources.Starship
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject

object ResourceSerializer : JsonContentPolymorphicSerializer<Resource>(Resource::class) {
    override fun selectDeserializer(element: JsonElement) = when {
        "gender" in element.jsonObject -> Hero.serializer()
        "starship_class" in element.jsonObject -> Starship.serializer()
        "diameter" in element.jsonObject -> Planet.serializer()
        else -> Film.serializer()
    }
}
package com.example.starwars.data.serializers

import com.example.starwars.data.model.Film
import com.example.starwars.data.model.Hero
import com.example.starwars.data.model.Planet
import com.example.starwars.data.model.Resource
import com.example.starwars.data.model.Starship
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject

object ResourceSerializer : JsonContentPolymorphicSerializer<Resource>(Resource::class) {
    override fun selectDeserializer(element: JsonElement) = when {
        "gender" in element.jsonObject -> Hero.serializer()
        "passengers" in element.jsonObject -> Starship.serializer()
        "diameter" in element.jsonObject -> Planet.serializer()
        else -> Film.serializer()
    }
}
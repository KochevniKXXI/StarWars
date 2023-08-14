package com.example.starwars.data.model

import com.example.starwars.data.serializers.ResourceSerializer
import com.example.starwars.ui.search.ResourceDetails
import kotlinx.serialization.Serializable

@Serializable(with = ResourceSerializer::class)
interface Resource {
    val url: String
    fun toResourceDetails(): ResourceDetails
    companion object : Resource {
        override val url: String = ""
        override fun toResourceDetails() = ResourceDetails
    }
}
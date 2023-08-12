package com.example.starwars.network.resources

import com.example.starwars.network.ResourceSerializer
import com.example.starwars.ui.home.ResourceDetails
import kotlinx.serialization.Serializable

@Serializable(with = ResourceSerializer::class)
interface Resource {
    fun toResourceDetails(): ResourceDetails
}
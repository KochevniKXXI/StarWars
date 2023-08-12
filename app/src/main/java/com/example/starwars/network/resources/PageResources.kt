package com.example.starwars.network.resources

import kotlinx.serialization.Serializable

@Serializable
data class PageResources(
    val next: String?,
    val results: List<Resource>
)

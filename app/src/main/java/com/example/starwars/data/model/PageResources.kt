package com.example.starwars.data.model

import kotlinx.serialization.Serializable

@Serializable
data class PageResources(
    val next: String?,
    val results: List<Resource>
)

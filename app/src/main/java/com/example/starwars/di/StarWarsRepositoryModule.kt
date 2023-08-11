package com.example.starwars.di

import com.example.starwars.data.NetworkStarWarsRepository
import com.example.starwars.data.StarWarsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class StarWarsRepositoryModule {

    @Binds
    abstract fun bindStarWarsRepository(networkStarWarsRepository: NetworkStarWarsRepository): StarWarsRepository
}
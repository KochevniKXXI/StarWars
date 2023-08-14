package com.example.starwars.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.example.starwars.data.serializers.UserFavoritesSerializer
import com.example.starwars.data.model.Resource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val DATA_STORE_FILENAME = "user_favorites.pb"

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideProtoDataStore(@ApplicationContext context: Context): DataStore<List<Resource>> {
        return DataStoreFactory.create(
            serializer = UserFavoritesSerializer,
            produceFile = { context.dataStoreFile(DATA_STORE_FILENAME) }
        )
    }
}
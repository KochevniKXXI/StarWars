package com.example.starwars.data.serializers

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.example.starwars.data.model.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object UserFavoritesSerializer : Serializer<List<Resource>> {
    override val defaultValue: List<Resource> = listOf()

    override suspend fun readFrom(input: InputStream): List<Resource> {
        try {
            return Json.decodeFromString(ListSerializer(ResourceSerializer), input.readBytes().decodeToString())
        } catch (serialization: SerializationException) {
            throw CorruptionException("Ошибка чтения избранного.", serialization)
        }
    }

    override suspend fun writeTo(t: List<Resource>, output: OutputStream) {
        withContext(Dispatchers.IO) {
            output.write(
                Json.encodeToString(ListSerializer(ResourceSerializer), t).encodeToByteArray()
            )
        }
    }
}
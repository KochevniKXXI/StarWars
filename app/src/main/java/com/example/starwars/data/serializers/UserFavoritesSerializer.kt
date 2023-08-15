package com.example.starwars.data.serializers

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.example.starwars.data.model.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.SetSerializer
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object UserFavoritesSerializer : Serializer<Set<Resource>> {
    override val defaultValue: Set<Resource> = setOf()

    override suspend fun readFrom(input: InputStream): Set<Resource> {
        try {
            return Json.decodeFromString(SetSerializer(ResourceSerializer), input.readBytes().decodeToString())
        } catch (serialization: SerializationException) {
            throw CorruptionException("Ошибка чтения избранного.", serialization)
        }
    }

    override suspend fun writeTo(t: Set<Resource>, output: OutputStream) {
        withContext(Dispatchers.IO) {
            output.write(
                Json.encodeToString(SetSerializer(ResourceSerializer), t).encodeToByteArray()
            )
        }
    }
}
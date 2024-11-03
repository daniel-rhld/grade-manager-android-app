package de.grademanger.core.datastore.serializer

import android.util.Log
import androidx.datastore.core.Serializer
import de.grademanger.core.datastore.model.GradeOrderingDataStore
import de.grademanger.core.datastore.model.GradeOrderingDirection
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object GradeOrderingDataStoreSerializer : Serializer<GradeOrderingDataStore> {
    override val defaultValue: GradeOrderingDataStore
        get() = GradeOrderingDataStore.ReceivedAt(
            direction = GradeOrderingDirection.DESCENDING
        )

    override suspend fun readFrom(input: InputStream): GradeOrderingDataStore {
        return try {
            Json.decodeFromString(
                deserializer = GradeOrderingDataStore.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: Exception) {
            defaultValue
        }
    }

    override suspend fun writeTo(t: GradeOrderingDataStore, output: OutputStream) {
        try {
            output.write(
                Json.encodeToString(
                    serializer = GradeOrderingDataStore.serializer(),
                    value = t
                ).encodeToByteArray()
            )
        } catch (e: Exception) {
            Log.e("GradeManager", "Failed to serialize GradeOrderingDataStore: ${e.stackTraceToString()}")
        }
    }
}
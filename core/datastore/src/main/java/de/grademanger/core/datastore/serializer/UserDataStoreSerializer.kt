package de.grademanger.core.datastore.serializer

import android.util.Log
import androidx.datastore.core.Serializer
import de.grademanger.core.datastore.model.grade_ordering.GradeOrderingDataStore
import de.grademanger.core.datastore.model.grade_ordering.GradeOrderingDirection
import de.grademanger.core.datastore.model.user.UserDataStore
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object UserDataStoreSerializer : Serializer<UserDataStore> {
    override val defaultValue: UserDataStore
        get() = UserDataStore(isLoggedIn = false)

    override suspend fun readFrom(input: InputStream): UserDataStore {
        return try {
            Json.decodeFromString(
                deserializer = UserDataStore.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: Exception) {
            defaultValue
        }
    }

    override suspend fun writeTo(t: UserDataStore, output: OutputStream) {
        try {
            output.write(
                Json.encodeToString(
                    serializer = UserDataStore.serializer(),
                    value = t
                ).encodeToByteArray()
            )
        } catch (e: Exception) {
            Log.e("GradeManager", "Failed to serialize UserDataStore: ${e.stackTraceToString()}")
        }
    }
}
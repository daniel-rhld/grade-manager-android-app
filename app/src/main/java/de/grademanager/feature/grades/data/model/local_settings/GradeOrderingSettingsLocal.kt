package de.grademanager.feature.grades.data.model.local_settings

import android.util.Log
import androidx.datastore.core.Serializer
import de.grademanager.feature.subjects.domain.model.GradeOrdering
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

@Serializable
sealed class GradeOrderingSettingsLocal {

    @Serializable
    data class ReceivedAt(val ascending: Boolean) : GradeOrderingSettingsLocal()

    @Serializable
    data class Value(val ascending: Boolean) : GradeOrderingSettingsLocal()

    object DataStoreSerializer : Serializer<GradeOrderingSettingsLocal> {
        override val defaultValue: GradeOrderingSettingsLocal
            get() = ReceivedAt(ascending = false)

        override suspend fun readFrom(input: InputStream): GradeOrderingSettingsLocal {
            return try {
                Json.decodeFromString(
                    deserializer = serializer(),
                    string = input.readBytes().decodeToString()
                )
            } catch (e: Exception) {
                defaultValue
            }
        }

        override suspend fun writeTo(t: GradeOrderingSettingsLocal, output: OutputStream) {
            try {
                output.write(
                    Json.encodeToString(
                        serializer = serializer(),
                        value = t
                    ).encodeToByteArray()
                )
            } catch (e: Exception) {
                Log.e("GradeManager", "Failed to serialize GradeOrderingSettingsLocal: ${e.stackTraceToString()}")
            }
        }

    }

}

fun GradeOrderingSettingsLocal.mapToDomainObject() = when (this) {
    is GradeOrderingSettingsLocal.ReceivedAt -> GradeOrdering.ReceivedAt(ascending)
    is GradeOrderingSettingsLocal.Value -> GradeOrdering.Value(ascending)
}
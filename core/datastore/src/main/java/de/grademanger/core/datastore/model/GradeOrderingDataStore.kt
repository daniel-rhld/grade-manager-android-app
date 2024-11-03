package de.grademanger.core.datastore.model

import de.grademanager.core.model.GradeOrdering
import kotlinx.serialization.Serializable

@Serializable
sealed class GradeOrderingDataStore {

    @Serializable
    data class Value(val direction: GradeOrderingDirection) : GradeOrderingDataStore()

    @Serializable
    data class ReceivedAt(val direction: GradeOrderingDirection) : GradeOrderingDataStore()

}

fun GradeOrderingDataStore.asExternalModel(): GradeOrdering = when (this) {
    is GradeOrderingDataStore.Value -> GradeOrdering.Value(
        direction = direction.asExternalModel()
    )

    is GradeOrderingDataStore.ReceivedAt -> GradeOrdering.ReceivedAt(
        direction = direction.asExternalModel()
    )
}

fun GradeOrdering.asDataStore(): GradeOrderingDataStore = when (this) {
    is GradeOrdering.Value -> GradeOrderingDataStore.Value(
        direction = direction.asDataStore()
    )

    is GradeOrdering.ReceivedAt -> GradeOrderingDataStore.ReceivedAt(
        direction = direction.asDataStore()
    )
}
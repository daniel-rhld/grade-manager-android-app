package de.grademanger.core.datastore.model.grade_ordering

import de.grademanager.core.model.OrderingDirection
import kotlinx.serialization.Serializable

@Serializable
enum class GradeOrderingDirection {
    ASCENDING, DESCENDING;
}

fun GradeOrderingDirection.asExternalModel(): OrderingDirection = when (this) {
    GradeOrderingDirection.ASCENDING -> OrderingDirection.ASCENDING
    GradeOrderingDirection.DESCENDING -> OrderingDirection.DESCENDING
}

fun OrderingDirection.asDataStore(): GradeOrderingDirection = when (this) {
    OrderingDirection.ASCENDING -> GradeOrderingDirection.ASCENDING
    OrderingDirection.DESCENDING -> GradeOrderingDirection.DESCENDING
}
package de.grademanager.core.model

sealed class GradeOrdering {

    data class Value(val direction: OrderingDirection) : GradeOrdering()
    data class ReceivedAt(val direction: OrderingDirection) : GradeOrdering()

}
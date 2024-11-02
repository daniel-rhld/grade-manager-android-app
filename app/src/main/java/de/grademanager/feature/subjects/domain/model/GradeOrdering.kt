package de.grademanager.feature.subjects.domain.model

sealed class GradeOrdering {

    data class Value(val ascending: Boolean) : GradeOrdering()
    data class ReceivedAt(val ascending: Boolean) : GradeOrdering()

}
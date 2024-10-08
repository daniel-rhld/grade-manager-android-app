package de.grademanager.feature.subjects.domain.models

sealed class GradeOrdering {

    data class Value(val ascending: Boolean) : GradeOrdering()
    data class ReceivedAt(val ascending: Boolean) : GradeOrdering()

}
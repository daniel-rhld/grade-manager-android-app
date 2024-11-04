package de.grademanager.core.model

enum class OrderingDirection {
    ASCENDING, DESCENDING;

    fun reverse(): OrderingDirection {
        return when (this) {
            ASCENDING -> DESCENDING
            DESCENDING -> ASCENDING
        }
    }
}
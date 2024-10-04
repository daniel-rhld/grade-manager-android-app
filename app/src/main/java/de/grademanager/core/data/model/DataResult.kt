package de.grademanager.core.data.model

sealed class DataResult <out T> {
    data class Success <T> (val value: T) : DataResult<T>()
    data class Failure(val error: StringWrapper?) : DataResult<Nothing>()
}
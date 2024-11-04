package de.grademanager.common.util

sealed class DataResult <out T> {
    data class Success <T> (val value: T) : DataResult<T>()
    data class Failure(val error: StringWrapper?) : DataResult<Nothing>()
}

fun <T> DataResult<T>.fold(
    onSuccess: (T) -> Unit,
    onFailure: (StringWrapper?) -> Unit
) {
    when (this) {
        is DataResult.Success -> onSuccess.invoke(this.value)
        is DataResult.Failure -> onFailure.invoke(this.error)
    }
}
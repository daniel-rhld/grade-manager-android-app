package de.grademanager.common.util

sealed class DataResult <out T> {
    data class Success <T> (val value: T) : DataResult<T>()
    data class Failure(val error: StringWrapper?) : DataResult<Nothing>()
}

suspend fun <T> DataResult<T>.fold(
    onSuccess: suspend (T) -> Unit,
    onFailure: suspend (StringWrapper?) -> Unit
) {
    when (this) {
        is DataResult.Success -> onSuccess.invoke(this.value)
        is DataResult.Failure -> onFailure.invoke(this.error)
    }
}
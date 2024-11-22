package de.grademanager.core.network

import de.grademanager.common.util.StringWrapper

sealed class RequestState <out R>  {

    data object Idle : RequestState<Nothing>()

    data object Loading : RequestState<Nothing>()

    data class Success <T> (val data: T) : RequestState<T>()

    data class Failure(val error: StringWrapper) : RequestState<Nothing>()

}

suspend fun <T> RequestState<T>.unwrap(
    idle: suspend () -> Unit = {},
    loading: suspend () -> Unit = {},
    success: suspend (T) -> Unit = {},
    failure: suspend (StringWrapper) -> Unit = {},
    finish: suspend () -> Unit = {}
) {
    if (this is RequestState.Idle) {
        idle.invoke()
    }

    if (this is RequestState.Loading) {
        loading.invoke()
    }

    if (this is RequestState.Success) {
        success.invoke(this.data)
        finish.invoke()
    }

    if (this is RequestState.Failure) {
        failure.invoke(this.error)
        finish.invoke()
    }
}
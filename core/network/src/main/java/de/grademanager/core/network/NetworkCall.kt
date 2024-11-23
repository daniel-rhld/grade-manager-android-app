package de.grademanager.core.network

import android.util.Log
import de.grademanager.common.util.StringWrapper
import de.grademanager.common.util.asStringWrapper
import de.grademanager.core.network.model.MessageResponse
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.serialization.json.Json

val SUCCESSFUL_STATUS_CODES = setOf(
    HttpStatusCode.OK,
    HttpStatusCode.Created,
    HttpStatusCode.Accepted
)

const val HTTP_CLIENT_LOGGING_TAG = "HttpClient"

inline fun <reified T> networkCall(
    tag: String,
    noinline call: suspend () -> HttpResponse,
    noinline onStart: suspend () -> Unit = {},
    noinline onFinish: suspend () -> Unit = {},
    noinline onSuccess: suspend (T) -> Unit = {},
    noinline onFailure: suspend () -> Unit = {},
): Flow<RequestState<T>> {
    return flow {
        try {
            onStart.invoke()

            emit(RequestState.Loading)

            val response = call.invoke()
            Log.d(HTTP_CLIENT_LOGGING_TAG, "[HttpClient][$tag] HTTP-Request executed with URL ${response.call.request.url}")

            if (response.status in SUCCESSFUL_STATUS_CODES) {
                try {
                    val responseData = response.body<T>()
                    emit(RequestState.Success(responseData))
                    onSuccess.invoke(responseData)
                } catch (e: Exception) {
                    onFailure.invoke()
                    emit(RequestState.Failure(response.getErrorMessage()))

                    Log.e(HTTP_CLIENT_LOGGING_TAG, "[HttpClient][$tag] Failed to parse JSON Response: ${e.stackTraceToString()}")
                }
            } else {
                onFailure.invoke()
                emit(RequestState.Failure(response.getErrorMessage()))

                Log.e(HTTP_CLIENT_LOGGING_TAG, "[HttpClient][$tag] Got unsuccessful HTTP-Response: Status code: ${response.status.value}, response content: ${response.bodyAsText()}}")
            }

            onFinish.invoke()
        } catch (e: Exception) {
            onFailure.invoke()
            emit(RequestState.Failure(
                error = e.message?.asStringWrapper() ?:
                R.string.core_network_default_error_message.asStringWrapper()
            ))

            Log.e(HTTP_CLIENT_LOGGING_TAG, "[HttpClient][$tag] Exception occurred: ${e.stackTraceToString()}}}")
        }

    }.onStart {
        emit(RequestState.Idle)
    }
}

suspend fun HttpResponse?.getErrorMessage(): StringWrapper {
    return try {
        if (this != null) {
            try {
                Json.decodeFromString<MessageResponse>(this.bodyAsText()).message
                    ?.asStringWrapper() ?:
                R.string.core_network_default_error_message.asStringWrapper()
            } catch (e: Exception) {
                R.string.core_network_default_error_message.asStringWrapper()
            }
        } else {
            R.string.core_network_default_error_message.asStringWrapper()
        }
    } catch (e: Exception) {
        R.string.core_network_default_error_message.asStringWrapper()
    }
}
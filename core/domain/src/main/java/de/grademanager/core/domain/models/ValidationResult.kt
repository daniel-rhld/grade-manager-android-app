package de.grademanager.core.domain.models

import de.grademanager.common.util.StringWrapper

sealed class ValidationResult {

    data object Success : ValidationResult()
    data class Failure(val error: StringWrapper) : ValidationResult()

}

fun ValidationResult.unwrap(
    onSuccess: () -> Unit,
    onFailure: (StringWrapper) -> Unit
) {
    if (this is ValidationResult.Success) {
        onSuccess.invoke()
    }

    if (this is ValidationResult.Failure) {
        onFailure.invoke(this.error)
    }
}
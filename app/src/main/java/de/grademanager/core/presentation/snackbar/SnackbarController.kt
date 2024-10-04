package de.grademanager.core.presentation.snackbar

import androidx.compose.material3.SnackbarDuration
import de.grademanager.core.data.model.StringWrapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random

class SnackbarController {

    data class Data(
        val message: StringWrapper,
        val action: Action?,
        val duration: SnackbarDuration,
    ) {
        data class Action(
            val label: StringWrapper,
            val action: () -> Unit
        )

        override fun hashCode() = Random.nextInt()
        override fun equals(other: Any?) = false
    }

    private val _errorSnackbar = MutableStateFlow<Data?>(value = null)
    val errorSnackbar = _errorSnackbar.asStateFlow()

    private val _neutralSnackbar = MutableStateFlow<Data?>(value = null)
    val neutralSnackbar = _neutralSnackbar.asStateFlow()

    suspend fun clearErrorSnackbarData() {
        _errorSnackbar.emit(null)
    }

    suspend fun clearNeutralSnackbarData() {
        _neutralSnackbar.emit(null)
    }

    suspend fun showErrorSnackbar(
        message: StringWrapper,
        duration: SnackbarDuration = SnackbarDuration.Short
    ) {
        _errorSnackbar.emit(
            value = Data(
                message = message,
                action = null,
                duration = duration
            )
        )
    }

    suspend fun showErrorSnackbar(
        message: StringWrapper,
        actionLabel: StringWrapper,
        onActionClick: () -> Unit,
        duration: SnackbarDuration = SnackbarDuration.Short
    ) {
        _errorSnackbar.emit(
            value = Data(
                message = message,
                action = Data.Action(
                    label = actionLabel,
                    action = onActionClick
                ),
                duration = duration
            )
        )
    }

    fun showNeutralSnackbar(
        message: StringWrapper,
        duration: SnackbarDuration = SnackbarDuration.Short
    ) {
        _neutralSnackbar.update {
            Data(
                message = message,
                action = null,
                duration = duration
            )
        }
    }

    suspend fun showNeutralSnackbar(
        message: StringWrapper,
        actionLabel: StringWrapper,
        onActionClick: () -> Unit,
        duration: SnackbarDuration = SnackbarDuration.Short
    ) {
        _neutralSnackbar.emit(
            value = Data(
                message = message,
                action = Data.Action(
                    label = actionLabel,
                    action = onActionClick
                ),
                duration = duration
            )
        )
    }

}
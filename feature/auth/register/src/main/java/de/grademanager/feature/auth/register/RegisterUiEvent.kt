package de.grademanager.feature.auth.register

sealed class RegisterUiEvent {

    data object NavigateToLoginRequested : RegisterUiEvent()

    data class FirstNameChange(val value: String) : RegisterUiEvent()

    data class LastNameChange(val value: String) : RegisterUiEvent()

    data class EmailAddressChange(val value: String) : RegisterUiEvent()

    data class PasswordChange(val value: String) : RegisterUiEvent()
    data object TogglePasswordVisibility : RegisterUiEvent()

    data class PasswordConfirmationChange(val value: String) : RegisterUiEvent()
    data object TogglePasswordConfirmationVisibility : RegisterUiEvent()

    data object ButtonRegisterClick : RegisterUiEvent()

}
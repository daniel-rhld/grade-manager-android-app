package de.grademanager.feature.auth.login

sealed class LoginUiEvent {

    data object NavigateToRegistrationRequested : LoginUiEvent()

    data class EmailAddressChange(val value: String) : LoginUiEvent()
    data class PasswordChange(val value: String) : LoginUiEvent()
    data object TogglePasswordVisibility : LoginUiEvent()

    data object ButtonLoginClick : LoginUiEvent()

}
package de.grademanager.feature.auth.register

data class RegisterUiState(
    val firstName: String,
    val lastName: String,
    val emailAddress: String,
    val password: String,
    val passwordVisible: Boolean,
    val passwordConfirmation: String,
    val passwordConfirmationVisible: Boolean,
    val buttonRegisterLoading: Boolean
)

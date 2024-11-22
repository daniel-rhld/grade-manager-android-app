package de.grademanager.feature.auth.login

data class LoginUiState(
    val emailAddress: String,
    val password: String,
    val passwordVisible: Boolean,
    val buttonLoginEnabled: Boolean,
    val buttonLoginLoading: Boolean
)

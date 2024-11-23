package de.grademanager.feature.auth.login

data class LoginUiState(
    val emailAddress: String,
    val password: String,
    val passwordVisible: Boolean,
    val buttonLoginLoading: Boolean
)

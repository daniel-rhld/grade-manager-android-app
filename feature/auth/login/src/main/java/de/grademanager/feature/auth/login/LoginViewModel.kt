package de.grademanager.feature.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.grademanager.common.util.asStringWrapper
import de.grademanager.core.descriptors.SnackbarType
import de.grademanager.core.domain.controller.snackbar.SnackbarController
import de.grademanager.core.domain.models.unwrap
import de.grademanager.core.domain.use_case.login.LoginUseCase
import de.grademanager.core.domain.use_case.validate_login_form.ValidateLoginFormUseCase
import de.grademanager.core.network.unwrap
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val validateLoginFormUseCase: ValidateLoginFormUseCase,
    private val loginUseCase: LoginUseCase,

    private val snackbarController: SnackbarController
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        LoginUiState(
            emailAddress = "",
            password = "",
            passwordVisible = false,
            buttonLoginLoading = false
        )
    )
    val uiState = _uiState.asStateFlow()

    private val _loginSucceeded = MutableStateFlow(false)
    val loginSucceeded = _loginSucceeded.asStateFlow()

    fun onUiEvent(event: LoginUiEvent) {
        when (event) {
            is LoginUiEvent.EmailAddressChange -> {
                _uiState.update {
                    it.copy(emailAddress = event.value)
                }
            }

            is LoginUiEvent.PasswordChange -> {
                _uiState.update {
                    it.copy(password = event.value)
                }
            }

            is LoginUiEvent.TogglePasswordVisibility -> {
                _uiState.update { state ->
                    state.copy(passwordVisible = !state.passwordVisible)
                }
            }

            is LoginUiEvent.ButtonLoginClick -> {
                val emailAddress = _uiState.value.emailAddress
                val password = _uiState.value.password

                validateLoginFormUseCase.invoke(
                    emailAddress = emailAddress,
                    password = password,
                    emptyEmailAddressErrorMessage = R.string.feature_auth_login_error_email_empty.asStringWrapper(),
                    invalidEmailAddressErrorMessage = R.string.feature_auth_login_error_invalid_email.asStringWrapper(),
                    emptyPasswordErrorMessage = R.string.feature_auth_login_error_password_empty.asStringWrapper()
                ).let { validationResult ->
                    validationResult.unwrap(
                        onSuccess = {
                            viewModelScope.launch {
                                loginUseCase.invoke(
                                    emailAddress = emailAddress,
                                    password = password
                                ).collectLatest { loginRequestState ->
                                    loginRequestState.unwrap(
                                        loading = {
                                            _uiState.update {
                                                it.copy(buttonLoginLoading = true)
                                            }
                                        },
                                        success = {
                                            _loginSucceeded.update { true }
                                        },
                                        failure = { error ->
                                            _uiState.update {
                                                it.copy(buttonLoginLoading = false)
                                            }
                                            snackbarController.showSnackbar {
                                                type = SnackbarType.ERROR
                                                message = error
                                            }
                                        }
                                    )
                                }
                            }
                        },
                        onFailure = { error ->
                            viewModelScope.launch {
                                snackbarController.showSnackbar {
                                    type = SnackbarType.ERROR
                                    message = error
                                }
                            }
                        }
                    )
                }
            }

            else -> Unit
        }
    }

}
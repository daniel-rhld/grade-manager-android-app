package de.grademanager.feature.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.grademanager.common.util.State
import de.grademanager.common.util.asStringWrapper
import de.grademanager.core.descriptors.SnackbarType
import de.grademanager.core.domain.controller.snackbar.SnackbarController
import de.grademanager.core.domain.models.unwrap
import de.grademanager.core.domain.use_case.login.LoginUseCase
import de.grademanager.core.domain.use_case.validate_login_form.ValidateLoginFormUseCase
import de.grademanager.core.network.unwrap
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val validateLoginFormUseCase: ValidateLoginFormUseCase,
    private val loginUseCase: LoginUseCase,

    private val snackbarController: SnackbarController
) : ViewModel() {

    private val _emailAddress = MutableStateFlow("")
    private val _password = MutableStateFlow("")

    val uiState = State(LoginUiState(
        emailAddress = "",
        password = "",
        passwordVisible = false,
        buttonLoginEnabled = false,
        buttonLoginLoading = false
    ))

    private val _loginSucceeded = MutableStateFlow(false)
    val loginSucceeded = _loginSucceeded.asStateFlow()

    init {
        viewModelScope.launch {
            combine(_emailAddress, _password) { emailAddress, password ->
                uiState.update {
                    it.copy(
                        emailAddress = emailAddress,
                        password = password,
                        buttonLoginEnabled = emailAddress.isNotBlank() && password.isNotBlank()
                    )
                }
            }.collect()
        }
    }

    fun onUiEvent(event: LoginUiEvent) {
        when (event) {
            is LoginUiEvent.EmailAddressChange -> {
                _emailAddress.update { event.value }
            }

            is LoginUiEvent.PasswordChange -> {
                _password.update { event.value }
            }

            is LoginUiEvent.TogglePasswordVisibility -> {
                uiState.update { state ->
                    state.copy(passwordVisible = !state.passwordVisible)
                }
            }

            is LoginUiEvent.ButtonLoginClick -> {
                val emailAddress = _emailAddress.value
                val password = _password.value

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
                                            uiState.update {
                                                it.copy(buttonLoginLoading = true)
                                            }
                                        },
                                        success = {
                                            _loginSucceeded.update { true }
                                        },
                                        failure = { error ->
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
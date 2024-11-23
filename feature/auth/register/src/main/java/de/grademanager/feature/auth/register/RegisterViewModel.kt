package de.grademanager.feature.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.grademanager.common.util.asStringWrapper
import de.grademanager.core.descriptors.SnackbarType
import de.grademanager.core.domain.controller.snackbar.SnackbarController
import de.grademanager.core.domain.models.unwrap
import de.grademanager.core.domain.use_case.login.LoginUseCase
import de.grademanager.core.domain.use_case.register.RegisterUseCase
import de.grademanager.core.domain.use_case.validate_register_form.ValidateRegisterFormUseCase
import de.grademanager.core.network.unwrap
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.math.log

class RegisterViewModel(
    private val registerUseCase: RegisterUseCase,
    private val loginUseCase: LoginUseCase,

    private val validateRegisterFormUseCase: ValidateRegisterFormUseCase,

    private val snackbarController: SnackbarController,
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        RegisterUiState(
            firstName = "",
            lastName = "",
            emailAddress = "",
            password = "",
            passwordVisible = false,
            passwordConfirmation = "",
            passwordConfirmationVisible = false,
            buttonRegisterLoading = false
        )
    )
    val uiState = _uiState.asStateFlow()

    private val _registrationSucceeded = MutableStateFlow(false)
    val registrationSucceeded = _registrationSucceeded.asStateFlow()

    fun onUiEvent(event: RegisterUiEvent) {
        when (event) {
            is RegisterUiEvent.FirstNameChange -> {
                _uiState.update {
                    it.copy(firstName = event.value)
                }
            }

            is RegisterUiEvent.LastNameChange -> {
                _uiState.update {
                    it.copy(lastName = event.value)
                }
            }

            is RegisterUiEvent.EmailAddressChange -> {
                _uiState.update {
                    it.copy(emailAddress = event.value)
                }
            }

            is RegisterUiEvent.PasswordChange -> {
                _uiState.update {
                    it.copy(password = event.value)
                }
            }

            is RegisterUiEvent.TogglePasswordVisibility -> {
                _uiState.update { state ->
                    state.copy(passwordVisible = !state.passwordVisible)
                }
            }

            is RegisterUiEvent.PasswordConfirmationChange -> {
                _uiState.update {
                    it.copy(passwordConfirmation = event.value)
                }
            }

            is RegisterUiEvent.TogglePasswordConfirmationVisibility -> {
                _uiState.update { state ->
                    state.copy(passwordConfirmationVisible = !state.passwordConfirmationVisible)
                }
            }

            is RegisterUiEvent.ButtonRegisterClick -> {
                val firstName = _uiState.value.firstName
                val lastName = _uiState.value.lastName
                val emailAddress = _uiState.value.emailAddress
                val password = _uiState.value.password
                val passwordConfirmation = _uiState.value.passwordConfirmation

                validateRegisterFormUseCase.invoke(
                    firstName = firstName,
                    lastName = lastName,
                    emailAddress = emailAddress,
                    password = password,
                    passwordConfirmation = passwordConfirmation,

                    firstNameEmptyErrorMessage = R.string.feature_auth_register_error_first_name_empty.asStringWrapper(),
                    lastNameEmptyErrorMessage = R.string.feature_auth_register_error_last_name_empty.asStringWrapper(),
                    emailAddressEmptyErrorMessage = R.string.feature_auth_register_error_email_empty.asStringWrapper(),
                    emailAddressInvalidErrorMessage = R.string.feature_auth_register_error_email_invalid.asStringWrapper(),
                    passwordEmptyErrorMessage = R.string.feature_auth_register_error_password_empty.asStringWrapper(),
                    passwordConfirmationEmptyErrorMessage = R.string.feature_auth_register_error_password_confirmation_empty.asStringWrapper(),
                    passwordConfirmationDoesNotMatchErrorMessage = R.string.feature_auth_register_error_password_confirmation_does_not_match.asStringWrapper()
                ).let { result ->
                    result.unwrap(
                        onSuccess = {
                            viewModelScope.launch {
                                registerUseCase.invoke(
                                    firstName = firstName,
                                    lastName = lastName,
                                    emailAddress = emailAddress,
                                    password = password,
                                    passwordConfirmation = passwordConfirmation
                                ).collectLatest { registerRequestState ->
                                    registerRequestState.unwrap(
                                        loading = {
                                            _uiState.update {
                                                it.copy(buttonRegisterLoading = true)
                                            }
                                        },
                                        success = { registerResponse ->
                                            if (registerResponse.success) {
                                                loginUseCase.invoke(
                                                    emailAddress = emailAddress,
                                                    password = password
                                                ).collectLatest { loginRequestState ->
                                                    loginRequestState.unwrap(
                                                        success = {
                                                            _registrationSucceeded.update { true }
                                                        },
                                                        failure = { errorMessage ->
                                                            snackbarController.showSnackbar {
                                                                type = SnackbarType.ERROR
                                                                message = errorMessage
                                                            }
                                                        }
                                                    )
                                                }
                                            } else {
                                                _uiState.update {
                                                    it.copy(buttonRegisterLoading = false)
                                                }

                                                snackbarController.showSnackbar {
                                                    type = SnackbarType.ERROR
                                                    message = (registerResponse.message ?: "An error occurred").asStringWrapper()
                                                }
                                            }
                                        },
                                        failure = { errorMessage ->
                                            _uiState.update {
                                                it.copy(buttonRegisterLoading = false)
                                            }

                                            snackbarController.showSnackbar {
                                                type = SnackbarType.ERROR
                                                message = errorMessage
                                            }
                                        }
                                    )
                                }
                            }
                        },
                        onFailure = { errorMessage ->
                            viewModelScope.launch {
                                snackbarController.showSnackbar {
                                    type = SnackbarType.ERROR
                                    message = errorMessage
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
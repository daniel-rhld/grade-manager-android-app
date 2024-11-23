package de.grademanager.feature.auth.register

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import de.grademanager.core.designsystem.components.AppSnackbarHost
import de.grademanager.core.designsystem.components.GradeManagerButton
import de.grademanager.core.designsystem.components.GradeManagerTextButton
import de.grademanager.core.designsystem.components.GradeManagerTextField
import de.grademanager.core.designsystem.components.GradeManagerTopAppBar
import de.grademanager.core.designsystem.components.LabeledDivider
import de.grademanager.core.designsystem.icons.GradeManagerIcons
import de.grademanager.core.designsystem.theme.AppAssets
import de.grademanager.core.designsystem.theme.GradeManagerTheme
import de.grademanager.core.domain.controller.snackbar.SnackbarController
import de.grademanager.core.ui.BrandLogo
import org.koin.compose.koinInject

@Composable
fun RegisterScreen(
    uiState: RegisterUiState,
    onUiEvent: (RegisterUiEvent) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    val lastNameFocusRequester = remember { FocusRequester() }
    val emailFocusRequester = remember { FocusRequester() }
    val passwordFocusRequester = remember { FocusRequester() }
    val passwordConfirmationFocusRequester = remember { FocusRequester() }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            GradeManagerTopAppBar(
                title = stringResource(R.string.feature_auth_register_toolbar_title)
            )
        },
        snackbarHost = {
            AppSnackbarHost()
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BrandLogo(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppAssets.spacing.screenSpacing)
                    .padding(
                        top = 48.dp,
                        bottom = 56.dp
                    )
            )

            GradeManagerTextField(
                value = uiState.firstName,
                onValueChange = { value ->
                    onUiEvent.invoke(
                        RegisterUiEvent.FirstNameChange(value = value)
                    )
                },
                placeholder = {
                    Text(
                        text = stringResource(R.string.feature_auth_register_text_field_firstname_placeholder)
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        lastNameFocusRequester.requestFocus()
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppAssets.spacing.screenSpacing)
            )

            Spacer(
                modifier = Modifier.height(AppAssets.spacing.verticalItemSpacing)
            )

            GradeManagerTextField(
                value = uiState.lastName,
                onValueChange = { value ->
                    onUiEvent.invoke(
                        RegisterUiEvent.LastNameChange(value = value)
                    )
                },
                placeholder = {
                    Text(
                        text = stringResource(R.string.feature_auth_register_text_field_lastname_placeholder)
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        emailFocusRequester.requestFocus()
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppAssets.spacing.screenSpacing)
                    .focusRequester(lastNameFocusRequester)
            )

            Spacer(
                modifier = Modifier.height(AppAssets.spacing.verticalItemSpacing)
            )

            GradeManagerTextField(
                value = uiState.emailAddress,
                onValueChange = { value ->
                    onUiEvent.invoke(
                        RegisterUiEvent.EmailAddressChange(value = value)
                    )
                },
                placeholder = {
                    Text(
                        text = stringResource(R.string.feature_auth_register_text_field_email_placeholder)
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        passwordFocusRequester.requestFocus()
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppAssets.spacing.screenSpacing)
            )

            Spacer(
                modifier = Modifier.height(AppAssets.spacing.verticalItemSpacing)
            )

            GradeManagerTextField(
                value = uiState.password,
                onValueChange = { value ->
                    onUiEvent.invoke(
                        RegisterUiEvent.PasswordChange(value = value)
                    )
                },
                placeholder = {
                    Text(
                        text = stringResource(R.string.feature_auth_register_text_field_password_placeholder)
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        passwordConfirmationFocusRequester.requestFocus()
                    }
                ),
                visualTransformation = if (uiState.passwordVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            onUiEvent.invoke(
                                RegisterUiEvent.TogglePasswordVisibility
                            )
                        }
                    ) {
                        Icon(
                            imageVector = if (uiState.passwordVisible) {
                                GradeManagerIcons.HidePassword
                            } else {
                                GradeManagerIcons.ShowPassword
                            },
                            contentDescription = null
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppAssets.spacing.screenSpacing)
            )

            Spacer(
                modifier = Modifier.height(AppAssets.spacing.verticalItemSpacing)
            )

            GradeManagerTextField(
                value = uiState.passwordConfirmation,
                onValueChange = { value ->
                    onUiEvent.invoke(
                        RegisterUiEvent.PasswordConfirmationChange(value = value)
                    )
                },
                placeholder = {
                    Text(
                        text = stringResource(R.string.feature_auth_register_text_field_password_confirmation_placeholder)
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus(true)
                        keyboardController?.hide()
                    }
                ),
                visualTransformation = if (uiState.passwordConfirmationVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            onUiEvent.invoke(
                                RegisterUiEvent.TogglePasswordConfirmationVisibility
                            )
                        }
                    ) {
                        Icon(
                            imageVector = if (uiState.passwordConfirmationVisible) {
                                GradeManagerIcons.HidePassword
                            } else {
                                GradeManagerIcons.ShowPassword
                            },
                            contentDescription = null
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppAssets.spacing.screenSpacing)
            )

            Spacer(
                modifier = Modifier.height(AppAssets.spacing.verticalItemSpacing * 1.5F)
            )

            GradeManagerButton(
                onClick = {
                    onUiEvent.invoke(
                        RegisterUiEvent.ButtonRegisterClick
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppAssets.spacing.screenSpacing)
            ) {
                if (!uiState.buttonRegisterLoading) {
                    Text(
                        text = stringResource(R.string.feature_auth_register_button_register_label),
                        modifier = Modifier
                    )
                } else {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        strokeWidth = 2.5.dp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(AppAssets.spacing.verticalItemSpacing * 1.5F)
            )

            LabeledDivider(
                label = stringResource(R.string.feature_auth_register_label_alternative_authentication_methods),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppAssets.spacing.screenSpacing)
            )

            Spacer(
                modifier = Modifier.height(AppAssets.spacing.verticalItemSpacing * 1.5F)
            )

            GradeManagerTextButton(
                onClick = {
                    onUiEvent.invoke(
                        RegisterUiEvent.NavigateToLoginRequested
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppAssets.spacing.screenSpacing)
            ) {
                Text(
                    text = stringResource(R.string.feature_auth_register_button_login_label)
                )
            }

            Spacer(
                modifier = Modifier.height(AppAssets.spacing.screenSpacing)
            )
        }
    }
}

class RegisterUiStatePreviewParameterProvider : PreviewParameterProvider<RegisterUiState> {
    override val values: Sequence<RegisterUiState>
        get() = sequenceOf(
            RegisterUiState(
                firstName = "",
                lastName = "",
                emailAddress = "",
                password = "",
                passwordVisible = false,
                passwordConfirmation = "",
                passwordConfirmationVisible = false,
                buttonRegisterLoading = false
            ),
            RegisterUiState(
                firstName = "John",
                lastName = "Doe",
                emailAddress = "john.doa@ateam.local",
                password = "Test123.",
                passwordVisible = false,
                passwordConfirmation = "Test123.",
                passwordConfirmationVisible = false,
                buttonRegisterLoading = false
            ),
            RegisterUiState(
                firstName = "John",
                lastName = "Doe",
                emailAddress = "john.doa@ateam.local",
                password = "Test123.",
                passwordVisible = true,
                passwordConfirmation = "Test123.",
                passwordConfirmationVisible = true,
                buttonRegisterLoading = false
            ),
            RegisterUiState(
                firstName = "John",
                lastName = "Doe",
                emailAddress = "john.doa@ateam.local",
                password = "Test123.",
                passwordVisible = false,
                passwordConfirmation = "Test123.",
                passwordConfirmationVisible = false,
                buttonRegisterLoading = true
            ),
        )
}

@PreviewLightDark
@Composable
private fun PreviewRegisterScreen(
    @PreviewParameter(RegisterUiStatePreviewParameterProvider::class)
    uiState: RegisterUiState
) {
    GradeManagerTheme {
        RegisterScreen(
            uiState = uiState,
            onUiEvent = {}
        )
    }
}
package de.grademanager.feature.auth.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Serializable
data object LoginScreenDestination

@Composable
fun LoginScreen(
    navigateToSubjectOverviewRequested: () -> Unit,
    navigateToRegistrationRequested: () -> Unit
) {
    val viewModel: LoginViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsState()
    val loginSucceeded by viewModel.loginSucceeded.collectAsState()

    LaunchedEffect(key1 = loginSucceeded) {
        if (loginSucceeded) {
            navigateToSubjectOverviewRequested.invoke()
        }
    }

    LoginScreen(
        uiState = uiState,
        onUiEvent = { event ->
            when (event) {
                is LoginUiEvent.NavigateToRegistrationRequested -> {
                    navigateToRegistrationRequested.invoke()
                }

                else -> viewModel.onUiEvent(event)
            }
        }
    )
}

@Composable
fun LoginScreen(
    uiState: LoginUiState,
    onUiEvent: (LoginUiEvent) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    val passwordFocusRequester = remember { FocusRequester() }
    
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            GradeManagerTopAppBar(
                title = stringResource(R.string.feature_auth_login_toolbar_title)
            )
        },
        snackbarHost = {
            AppSnackbarHost()
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
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
                value = uiState.emailAddress,
                onValueChange = { value ->
                    onUiEvent.invoke(
                        LoginUiEvent.EmailAddressChange(value = value)
                    )
                },
                placeholder = {
                    Text(
                        text = stringResource(R.string.feature_auth_login_text_field_email_address_placeholder)
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
                        LoginUiEvent.PasswordChange(value = value)
                    )
                },
                placeholder = {
                    Text(
                        text = stringResource(R.string.feature_auth_login_text_field_password_placeholder)
                    )
                },
                visualTransformation = if (uiState.passwordVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Send
                ),
                keyboardActions = KeyboardActions(
                    onSend = {
                        focusManager.clearFocus(true)
                        keyboardController?.hide()
                        
                        onUiEvent.invoke(
                            LoginUiEvent.ButtonLoginClick
                        )
                    }
                ),
                trailingIcon = {
                    IconButton(
                        onClick = {
                            onUiEvent.invoke(
                                LoginUiEvent.TogglePasswordVisibility
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
                    .focusRequester(passwordFocusRequester)
            )

            Spacer(
                modifier = Modifier.height(AppAssets.spacing.verticalItemSpacing * 1.5F)
            )

            GradeManagerButton(
                onClick = {
                    onUiEvent.invoke(
                        LoginUiEvent.ButtonLoginClick
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppAssets.spacing.screenSpacing)
            ) {
                if (!uiState.buttonLoginLoading) {
                    Text(
                        text = stringResource(R.string.feature_auth_login_button_login_label),
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
                label = stringResource(R.string.feature_auth_login_label_alternative_authentication_methods),
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
                        LoginUiEvent.NavigateToRegistrationRequested
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppAssets.spacing.screenSpacing)
            ) {
                Text(
                    text = stringResource(R.string.feature_auth_login_button_register_label)
                )
            }

            Spacer(
                modifier = Modifier.height(AppAssets.spacing.screenSpacing)
            )
        }
    }
}

class LoginUiStatePreviewParameterProvider : PreviewParameterProvider<LoginUiState> {
    override val values: Sequence<LoginUiState>
        get() = sequenceOf(
            LoginUiState(
                emailAddress = "",
                password = "",
                passwordVisible = false,
                buttonLoginLoading = false
            ),
            LoginUiState(
                emailAddress = "daniel.reinhold@protonmail.com",
                password = "",
                passwordVisible = false,
                buttonLoginLoading = false
            ),
            LoginUiState(
                emailAddress = "",
                password = "Test123.",
                passwordVisible = true,
                buttonLoginLoading = false
            ),
            LoginUiState(
                emailAddress = "daniel.reinhold@protonmail.com",
                password = "Test123.",
                passwordVisible = false,
                buttonLoginLoading = false
            ),
            LoginUiState(
                emailAddress = "daniel.reinhold@protonmail.com",
                password = "Test123.",
                passwordVisible = true,
                buttonLoginLoading = true
            )
        )
}

@PreviewLightDark
@Composable
private fun PreviewLoginScreen(
    @PreviewParameter(LoginUiStatePreviewParameterProvider::class)
    uiState: LoginUiState
) {
    GradeManagerTheme {
        LoginScreen(
            uiState = uiState,
            onUiEvent = {}
        )
    }
}
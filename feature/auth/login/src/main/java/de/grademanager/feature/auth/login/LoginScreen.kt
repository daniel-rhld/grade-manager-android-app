package de.grademanager.feature.auth.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DeleteOutline
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.Key
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
import de.grademanager.core.designsystem.components.GradeManagerTopAppBar
import de.grademanager.core.designsystem.theme.AppAssets
import de.grademanager.core.designsystem.theme.GradeManagerTheme
import de.grademanager.core.domain.controller.snackbar.SnackbarController
import de.grademanager.core.ui.extensions.collectAsState
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject
import kotlin.math.log

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
            AppSnackbarHost(
                snackbarData = koinInject<SnackbarController>().snackbarData
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
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
                        keyboardController?.show()
                    }
                ),
                shape = RoundedCornerShape(size = 12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppAssets.spacing.screenSpacing)
            )

            Spacer(
                modifier = Modifier.height(AppAssets.spacing.verticalItemSpacing)
            )

            OutlinedTextField(
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
                                Icons.Rounded.VisibilityOff
                            } else {
                                Icons.Rounded.Visibility
                            },
                            contentDescription = null
                        )
                    }
                },
                shape = RoundedCornerShape(size = 12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppAssets.spacing.screenSpacing)
                    .focusRequester(passwordFocusRequester)
            )

            Spacer(
                modifier = Modifier.height(AppAssets.spacing.verticalItemSpacing * 1.5F)
            )

            Button(
                onClick = {
                    onUiEvent.invoke(
                        LoginUiEvent.ButtonLoginClick
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppAssets.spacing.screenSpacing)
            ) {
                Text(
                    text = stringResource(R.string.feature_auth_login_button_login_label),
                    modifier = Modifier
                )
            }

            Spacer(
                modifier = Modifier.height(AppAssets.spacing.verticalItemSpacing * 1.5F)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppAssets.spacing.screenSpacing)
            ) {
                HorizontalDivider(
                    modifier = Modifier.weight(1F)
                )

                Text(
                    text = stringResource(R.string.feature_auth_login_label_alternative_authentication_methods),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light,
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier.padding(horizontal = AppAssets.spacing.screenSpacing)
                )

                HorizontalDivider(
                    modifier = Modifier.weight(1F)
                )
            }

            Spacer(
                modifier = Modifier.height(AppAssets.spacing.verticalItemSpacing * 1.5F)
            )


            TextButton(
                onClick = {
                    onUiEvent.invoke(
                        LoginUiEvent.ButtonLoginClick
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppAssets.spacing.screenSpacing)
            ) {
                Text(
                    text = stringResource(R.string.feature_auth_login_button_register_label),
                    modifier = Modifier
                )
            }
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
                buttonLoginEnabled = false,
                buttonLoginLoading = false
            ),
            LoginUiState(
                emailAddress = "daniel.reinhold@protonmail.com",
                password = "",
                passwordVisible = false,
                buttonLoginEnabled = false,
                buttonLoginLoading = false
            ),
            LoginUiState(
                emailAddress = "",
                password = "Test123.",
                passwordVisible = true,
                buttonLoginEnabled = false,
                buttonLoginLoading = false
            ),
            LoginUiState(
                emailAddress = "daniel.reinhold@protonmail.com",
                password = "Test123.",
                passwordVisible = false,
                buttonLoginEnabled = true,
                buttonLoginLoading = false
            ),
            LoginUiState(
                emailAddress = "daniel.reinhold@protonmail.com",
                password = "Test123.",
                passwordVisible = true,
                buttonLoginEnabled = true,
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
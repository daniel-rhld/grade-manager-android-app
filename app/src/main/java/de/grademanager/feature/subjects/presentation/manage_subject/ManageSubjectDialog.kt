package de.grademanager.feature.subjects.presentation.manage_subject

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.grademanager.R
import de.grademanager.core.data.model.StringWrapper
import de.grademanager.core.data.model.get
import de.grademanager.core.presentation.theme.AppAssets
import de.grademanager.core.presentation.theme.GradeManagerTheme
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageSubjectDialog(
    mode: ManageSubjectMode,
    uiState: ManageSubjectDialogUiState,
    onUiEvent: (ManageSubjectDialogUiEvent) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val nameFocusRequester = remember { FocusRequester() }

    LaunchedEffect(key1 = Unit) {
        delay(200)
        nameFocusRequester.requestFocus()
        keyboardController?.show()
    }

    ModalBottomSheet(
        onDismissRequest = {
            onUiEvent.invoke(
                ManageSubjectDialogUiEvent.DismissRequested
            )
        }
    ) {
        ManageSubjectDialogContent(
            mode = mode,
            uiState = uiState,
            onUiEvent = onUiEvent,
            nameFocusRequester = nameFocusRequester
        )
    }
}

@Composable
fun ManageSubjectDialogContent(
    mode: ManageSubjectMode,
    uiState: ManageSubjectDialogUiState,
    onUiEvent: (ManageSubjectDialogUiEvent) -> Unit,
    nameFocusRequester: FocusRequester = remember { FocusRequester() }
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(
            modifier = Modifier.height(AppAssets.spacing.bottomDialogContentSpacing)
        )

        Text(
            text = when (mode) {
                ManageSubjectMode.Create -> R.string.manage_subject_dialog_create_title
                ManageSubjectMode.Update -> R.string.manage_subject_dialog_update_title
            }.let { stringRes ->
                stringResource(id = stringRes)
            },
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(AppAssets.spacing.verticalItemSpacing)
        )

        OutlinedTextField(
            value = uiState.name,
            onValueChange = { value ->
                onUiEvent.invoke(
                    ManageSubjectDialogUiEvent.NameChange(value = value)
                )
            },
            shape = RoundedCornerShape(size = 12.dp),
            placeholder = {
                Text(
                    text = stringResource(R.string.manage_subject_dialog_name_hint),
                )
            },
            isError = uiState.nameError != null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppAssets.spacing.bottomDialogContentSpacing)
                .focusRequester(
                    focusRequester = nameFocusRequester
                )
        )

        AnimatedVisibility(
            visible = uiState.nameError != null
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = AppAssets.spacing.bottomDialogContentSpacing + 4.dp
                    )
            ) {
                Spacer(
                    modifier = Modifier.height(2.dp)
                )

                Text(
                    text = uiState.nameError?.get() ?: "",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }

        Spacer(
            modifier = Modifier.height(AppAssets.spacing.verticalItemSpacing)
        )

        Button(
            onClick = {
                onUiEvent.invoke(
                    ManageSubjectDialogUiEvent.ButtonSaveClick
                )
            },
            enabled = uiState.buttonSaveEnabled,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppAssets.spacing.bottomDialogContentSpacing)
        ) {
            Icon(
                imageVector = Icons.Rounded.Check,
                contentDescription = null
            )

            Spacer(
                modifier = Modifier.width(8.dp)
            )

            Text(
                text = stringResource(R.string.manage_subject_dialog_button_save)
            )
        }

        Spacer(
            modifier = Modifier.height(AppAssets.spacing.bottomDialogContentSpacing)
        )
    }
}

@PreviewLightDark
@Composable
private fun PreviewManageSubjectDialogContentModeSave() {
    GradeManagerTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            ManageSubjectDialogContent(
                mode = ManageSubjectMode.Create,
                uiState = ManageSubjectDialogUiState(
                    name = "",
                    nameError = null,
                    buttonSaveEnabled = false,
                ),
                onUiEvent = {}
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewManageSubjectDialogContentModeUpdate() {
    GradeManagerTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            ManageSubjectDialogContent(
                mode = ManageSubjectMode.Update,
                uiState = ManageSubjectDialogUiState(
                    name = "",
                    nameError = StringWrapper.Value(value = "Error"),
                    buttonSaveEnabled = false
                ),
                onUiEvent = {}
            )
        }
    }
}
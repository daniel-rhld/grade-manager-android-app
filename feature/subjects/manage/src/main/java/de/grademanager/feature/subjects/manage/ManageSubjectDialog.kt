package de.grademanager.feature.subjects.manage

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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.grademanager.common.util.asStringWrapper
import de.grademanager.core.designsystem.components.GradeManagerBottomSheet
import de.grademanager.core.designsystem.theme.AppAssets
import de.grademanager.core.designsystem.theme.GradeManagerTheme
import de.grademanager.core.ui.extensions.get

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageSubjectDialog(
    uiState: ManageSubjectDialogUiState,
    onUiEvent: (ManageSubjectDialogUiEvent) -> Unit
) {
    GradeManagerBottomSheet(
        onDismissRequest = {
            onUiEvent.invoke(
                ManageSubjectDialogUiEvent.DismissRequested
            )
        }
    ) {
        ManageSubjectDialogContent(
            uiState = uiState,
            onUiEvent = onUiEvent
        )
    }
}

@Composable
fun ManageSubjectDialogContent(
    uiState: ManageSubjectDialogUiState,
    onUiEvent: (ManageSubjectDialogUiEvent) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(
            modifier = Modifier.height(AppAssets.spacing.bottomDialogContentSpacing)
        )

        Text(
            text = when (uiState.mode) {
                ManageSubjectMode.Create -> R.string.feature_subjects_manage_create_title
                ManageSubjectMode.Update -> R.string.feature_subjects_manage_update_title
            }.let { stringRes ->
                stringResource(id = stringRes)
            },
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(AppAssets.spacing.verticalItemSpacing)
        )

        HorizontalDivider()

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
                    text = stringResource(R.string.feature_subjects_manage_name_hint),
                )
            },
            isError = uiState.nameError != null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppAssets.spacing.bottomDialogContentSpacing)
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
                text = stringResource(R.string.feature_subjects_manage_button_save)
            )
        }

        Spacer(
            modifier = Modifier.height(AppAssets.spacing.bottomDialogContentSpacing)
        )
    }
}

class ManageSubjectDialogUiStatePreviewParameterProvider :
    PreviewParameterProvider<ManageSubjectDialogUiState> {

    override val values: Sequence<ManageSubjectDialogUiState>
        get() = sequenceOf(
            ManageSubjectDialogUiState(
                mode = ManageSubjectMode.Create,
                name = "",
                nameError = null,
                buttonSaveEnabled = false
            ),
            ManageSubjectDialogUiState(
                mode = ManageSubjectMode.Create,
                name = "Deutsch",
                nameError = null,
                buttonSaveEnabled = true
            ),
            ManageSubjectDialogUiState(
                mode = ManageSubjectMode.Create,
                name = "Deutsch",
                nameError = "Dieses Fach existiert bereits".asStringWrapper(),
                buttonSaveEnabled = true
            ),
            ManageSubjectDialogUiState(
                mode = ManageSubjectMode.Update,
                name = "",
                nameError = null,
                buttonSaveEnabled = false
            ),
            ManageSubjectDialogUiState(
                mode = ManageSubjectMode.Update,
                name = "Deutsch",
                nameError = null,
                buttonSaveEnabled = true
            ),
            ManageSubjectDialogUiState(
                mode = ManageSubjectMode.Update,
                name = "Deutsch",
                nameError = "Dieses Fach existiert bereits".asStringWrapper(),
                buttonSaveEnabled = true
            ),
        )

}

@PreviewLightDark
@Composable
private fun PreviewManageSubjectDialogContentModeUpdate(
    @PreviewParameter(ManageSubjectDialogUiStatePreviewParameterProvider::class)
    uiState: ManageSubjectDialogUiState
) {
    GradeManagerTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            ManageSubjectDialogContent(
                uiState = uiState,
                onUiEvent = {}
            )
        }
    }
}
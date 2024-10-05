package de.grademanager.feature.grades.presentation.add_grade

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.grademanager.R
import de.grademanager.core.domain.ext.formatWeighting
import de.grademanager.core.presentation.theme.AppAssets
import de.grademanager.core.presentation.theme.GradeManagerTheme
import de.grademanager.feature.grades.presentation.add_grade.components.GradeInputField
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddGradeDialog(
    uiState: AddGradeDialogUiState,
    onUiEvent: (AddGradeDialogUiEvent) -> Unit
) {
    ModalBottomSheet(
        sheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = true
        ),
        onDismissRequest = {
            onUiEvent.invoke(
                AddGradeDialogUiEvent.DismissRequested
            )
        }
    ) {
        AddGradeDialogContent(
            uiState = uiState,
            onUiEvent = onUiEvent
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddGradeDialogContent(
    uiState: AddGradeDialogUiState,
    onUiEvent: (AddGradeDialogUiEvent) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(
                state = rememberScrollState()
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(
            modifier = Modifier.height(AppAssets.spacing.bottomDialogContentSpacing)
        )

        Text(
            text = stringResource(R.string.add_grade_dialog_title),
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

        Text(
            text = stringResource(R.string.add_grade_dialog_hint_grade),
            fontSize = 14.sp
        )

        Spacer(
            modifier = Modifier.height(2.dp)
        )

        GradeInputField(
            grade = uiState.grade,
            onGradeChange = { value ->
                onUiEvent.invoke(
                    AddGradeDialogUiEvent.GradeChanged(value = value)
                )
            }
        )

        Spacer(
            modifier = Modifier.height(AppAssets.spacing.verticalItemSpacing)
        )

        Text(
            text = stringResource(R.string.add_grade_dialog_hint_weighting),
            fontSize = 14.sp
        )

        Spacer(
            modifier = Modifier.height(2.dp)
        )

        Slider(
            value = uiState.weighting.onScale.toFloat(),
            steps = AddGradeDialogUiState.GradeWeighting.entries.size - 2,
            thumb = {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(
                            shape = RoundedCornerShape(percent = 50)
                        )
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = uiState.weighting.weighting.formatWeighting(),
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            },
            onValueChange = { weighting ->
                val formatted = String.format(
                    Locale.ENGLISH,
                    "%.1f",
                    weighting
                ).toDouble()

                onUiEvent.invoke(
                    AddGradeDialogUiEvent.WeightingChanged(
                        value = formatted
                    )
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppAssets.spacing.bottomDialogContentSpacing)
        )

        Spacer(
            modifier = Modifier.height(AppAssets.spacing.verticalItemSpacing)
        )

        Text(
            text = stringResource(R.string.add_grade_dialog_hint_received_at),
            fontSize = 14.sp
        )

        Spacer(
            modifier = Modifier.height(2.dp)
        )

        OutlinedTextField(
            value = SimpleDateFormat(
                stringResource(R.string.date_format_short),
                Locale.getDefault()
            ).format(uiState.receivedAt),
            onValueChange = {},
            readOnly = true,
            shape = RoundedCornerShape(size = 12.dp),
            trailingIcon = {
                FilledIconButton(
                    onClick = {
                        onUiEvent.invoke(
                            AddGradeDialogUiEvent.ChangeReceivedAtRequested
                        )
                    },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Edit,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppAssets.spacing.bottomDialogContentSpacing)
        )

        Spacer(
            modifier = Modifier.height(AppAssets.spacing.verticalItemSpacing)
        )

        Text(
            text = stringResource(R.string.add_grade_dialog_hint_description),
            fontSize = 14.sp
        )

        Spacer(
            modifier = Modifier.height(2.dp)
        )

        OutlinedTextField(
            value = uiState.description,
            onValueChange = { value ->
                onUiEvent.invoke(
                    AddGradeDialogUiEvent.DescriptionChanged(
                        value = value
                    )
                )
            },
            shape = RoundedCornerShape(size = 12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppAssets.spacing.bottomDialogContentSpacing)
        )

        Spacer(
            modifier = Modifier.height(AppAssets.spacing.verticalItemSpacing)
        )

        Button(
            onClick = {
                onUiEvent.invoke(
                    AddGradeDialogUiEvent.ButtonSaveClick
                )
            },
            enabled = uiState.buttonSaveEnabled,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppAssets.spacing.bottomDialogContentSpacing)
        ) {
            Icon(
                imageVector = Icons.Rounded.Check,
                contentDescription = null,
            )

            Spacer(
                modifier = Modifier.width(8.dp)
            )

            Text(
                text = stringResource(R.string.add_grade_dialog_button_save)
            )
        }

        Spacer(
            modifier = Modifier.height(AppAssets.spacing.bottomDialogContentSpacing)
        )
    }
}

@PreviewLightDark
@Composable
private fun PreviewAddGradeDialogContent() {
    var weighting by remember { mutableStateOf(AddGradeDialogUiState.GradeWeighting.QUARTER) }

    LaunchedEffect(key1 = weighting) {
        Log.i("GradeManager", "Weighting is now $weighting")
    }

    GradeManagerTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            AddGradeDialogContent(
                uiState = AddGradeDialogUiState(
                    grade = "",
                    weighting = weighting,
                    description = "",
                    receivedAt = Date(),
                    buttonSaveEnabled = false
                ),
                onUiEvent = { event ->
                    if (event is AddGradeDialogUiEvent.WeightingChanged) {
                        AddGradeDialogUiState.GradeWeighting.findByScale(
                            value = event.value
                        )?.let {
                            weighting = it
                        }
                    }
                }
            )
        }
    }
}
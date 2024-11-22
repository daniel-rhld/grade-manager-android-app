package de.grademanager.feature.subjects.overview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import de.grademanager.core.designsystem.components.AppSnackbarHost
import de.grademanager.core.designsystem.components.GradeManagerTopAppBar
import de.grademanager.core.designsystem.theme.AppAssets
import de.grademanager.core.designsystem.theme.GradeManagerTheme
import de.grademanager.core.domain.controller.snackbar.SnackbarController
import de.grademanager.core.mock.ModelMock
import de.grademanager.core.ui.BottomAverageGradeComponent
import de.grademanager.core.ui.NoItemsIndicator
import de.grademanager.core.ui.SubjectComponent
import de.grademanager.core.ui.extensions.collectAsState
import de.grademanager.feature.subjects.manage.ManageSubjectDialog
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Serializable
data object SubjectsOverviewScreenDestination

@Composable
fun SubjectsOverviewScreen(
    navigateToSubjectRequested: (subjectId: Int) -> Unit
) {
    val viewModel: SubjectsOverviewViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsState()

    val createSubjectDialogVisible by viewModel.createSubjectDialogVisible.collectAsState()
    val createSubjectDialogUiState by viewModel.createSubjectDialogUiState.collectAsState()

    val updateSubjectDialogVisible by viewModel.updateSubjectDialogVisible.collectAsState()
    val updateSubjectDialogUiState by viewModel.updateSubjectDialogUiState.collectAsState()

    if (createSubjectDialogVisible) {
        ManageSubjectDialog(
            uiState = createSubjectDialogUiState,
            onUiEvent = viewModel::onCreateSubjectDialogUiEvent
        )
    }

    if (updateSubjectDialogVisible) {
        ManageSubjectDialog(
            uiState = updateSubjectDialogUiState,
            onUiEvent = viewModel::onUpdateSubjectDialogUiEvent
        )
    }

    SubjectsOverviewScreen(
        uiState = uiState,
        onUiEvent = { event ->
            when (event) {
                is SubjectOverviewUiEvent.SubjectClick -> navigateToSubjectRequested.invoke(
                    event.subject.id
                )

                is SubjectOverviewUiEvent.SubjectLongClick -> {
                    // TODO: Add vibration effect

                    viewModel.onUiEvent(event = event)
                }

                else -> viewModel.onUiEvent(event = event)
            }
        }
    )
}

@Composable
fun SubjectsOverviewScreen(
    uiState: SubjectOverviewUiState,
    onUiEvent: (SubjectOverviewUiEvent) -> Unit
) {
    Scaffold(
        topBar = {
            GradeManagerTopAppBar(
                title = stringResource(R.string.feature_subjects_overview_toolbar_title)
            )
        },
        floatingActionButton = {
            if (uiState.subjects.isNotEmpty()) {
                FloatingActionButton(
                    onClick = {
                        onUiEvent.invoke(
                            SubjectOverviewUiEvent.ButtonCreateFirstSubjectClick
                        )
                    },
                    modifier = Modifier.padding(
                        bottom = if (uiState.overallGradesVisible) {
                            AppAssets.sizes.bottomGradeAverageComponentHeight
                        } else {
                            0.dp
                        }
                    )
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = null
                    )
                }
            }
        },
        snackbarHost = {
            AppSnackbarHost(
                snackbarData = koinInject<SnackbarController>().snackbarData
            )
        }
    ) { padding ->
        if (uiState.subjects.isNotEmpty()) {
            Column(
                modifier = Modifier.padding(padding)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1F),
                    contentPadding = PaddingValues(
                        top = AppAssets.spacing.screenSpacing,
                        end = AppAssets.spacing.screenSpacing,
                        start = AppAssets.spacing.screenSpacing,
                        bottom = if (uiState.overallGradesVisible) {
                            AppAssets.scaffoldWithFabBottomPadding
                        } else {
                            AppAssets.spacing.screenSpacing
                        }
                    ),
                    verticalArrangement = Arrangement.spacedBy(AppAssets.spacing.verticalItemSpacing)
                ) {
                    items(
                        items = uiState.subjects,
                        key = { it.self.id }
                    ) { subject ->
                        SubjectComponent(
                            subject = subject.self,
                            averageGrade = subject.averageGrade,
                            onClick = {
                                onUiEvent.invoke(
                                    SubjectOverviewUiEvent.SubjectClick(subject = subject.self)
                                )
                            },
                            onLongClick = {
                                onUiEvent.invoke(
                                    SubjectOverviewUiEvent.SubjectLongClick(subject = subject.self)
                                )
                            },
                            modifier = Modifier.fillMaxWidth().animateItem()
                        )
                    }
                }

                if (uiState.overallGradesVisible) {
                    BottomAverageGradeComponent(
                        label = stringResource(R.string.feature_subjects_overview_grade_average_label),
                        grade = uiState.averageGrade
                    )
                }
            }
        } else {
            NoItemsIndicator(
                label = stringResource(R.string.feature_subjects_overview_no_subjects_created_label),
                actionLabel = stringResource(R.string.feature_subjects_overview_button_create_first_subjects),
                onActionClick = {
                    onUiEvent.invoke(
                        SubjectOverviewUiEvent.ButtonCreateFirstSubjectClick
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(padding)
                    .padding(AppAssets.spacing.screenSpacing)
            )
        }
    }
}

private class SubjectOverviewUiStatePreviewParameterProvider : PreviewParameterProvider<SubjectOverviewUiState> {
    override val values: Sequence<SubjectOverviewUiState>
        get() = sequenceOf(
            SubjectOverviewUiState(
                subjects = listOf(ModelMock.SubjectWithAverageGrade),
                overallGradesVisible = true,
                averageGrade = 1.75
            ),
            SubjectOverviewUiState(
                subjects = listOf(ModelMock.SubjectWithAverageGrade),
                overallGradesVisible = false,
                averageGrade = 0.0
            )
        )
}

@PreviewLightDark
@Composable
private fun PreviewSubjectsOverviewScreen(
    @PreviewParameter(SubjectOverviewUiStatePreviewParameterProvider::class)
    uiState: SubjectOverviewUiState
) {
    GradeManagerTheme {
        SubjectsOverviewScreen(
            uiState = uiState,
            onUiEvent = {}
        )
    }
}
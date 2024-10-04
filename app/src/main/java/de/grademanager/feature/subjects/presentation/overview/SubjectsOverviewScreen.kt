package de.grademanager.feature.subjects.presentation.overview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import de.grademanager.R
import de.grademanager.core.presentation.components.DefaultTopAppBar
import de.grademanager.core.presentation.effects.vibrate
import de.grademanager.core.presentation.theme.AppAssets
import de.grademanager.core.presentation.theme.GradeManagerTheme
import de.grademanager.feature.subjects.domain.models.SubjectMock
import de.grademanager.feature.subjects.presentation.manage_subject.ManageSubjectDialog
import de.grademanager.feature.subjects.presentation.manage_subject.ManageSubjectMode
import de.grademanager.feature.subjects.presentation.overview.components.NoSubjectsIndicator
import de.grademanager.feature.subjects.presentation.overview.components.SubjectComponent
import org.koin.androidx.compose.koinViewModel

@Destination<RootGraph>(start = true)
@Composable
fun SubjectsOverviewScreen(
    navigator: DestinationsNavigator
) {
    val context = LocalContext.current

    val viewModel: SubjectsOverviewViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsState()

    val createSubjectDialogVisible by viewModel.createSubjectDialogVisible.collectAsState()
    val createSubjectDialogUiState by viewModel.createSubjectDialogUiState.collectAsState()

    val updateSubjectDialogVisible by viewModel.updateSubjectDialogVisible.collectAsState()
    val updateSubjectDialogUiState by viewModel.updateSubjectDialogUiState.collectAsState()

    if (createSubjectDialogVisible) {
        ManageSubjectDialog(
            mode = ManageSubjectMode.Create,
            uiState = createSubjectDialogUiState,
            onUiEvent = viewModel::onCreateSubjectDialogUiEvent
        )
    }

    if (updateSubjectDialogVisible) {
        ManageSubjectDialog(
            mode = ManageSubjectMode.Update,
            uiState = updateSubjectDialogUiState,
            onUiEvent = viewModel::onUpdateSubjectDialogUiEvent
        )
    }

    SubjectsOverviewScreen(
        uiState = uiState,
        onUiEvent = { event ->
            when (event) {
                is SubjectOverviewUiEvent.SubjectClick -> {
                    // TODO: Navigate to subject
                }

                is SubjectOverviewUiEvent.SubjectLongClick -> {
                    vibrate(
                        context = context,
                        duration = 20
                    )

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
            DefaultTopAppBar(
                title = stringResource(R.string.subjects_overview_toolbar_title),
                actions = {
                    if (uiState.subjects.isNotEmpty()) {
                        IconButton(
                            onClick = {
                                onUiEvent.invoke(
                                    SubjectOverviewUiEvent.ButtonCreateFirstSubjectClick
                                )
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Add,
                                contentDescription = null
                            )
                        }
                    }
                }
            )
        },
    ) { padding ->
        if (uiState.subjects.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(padding),
                contentPadding = PaddingValues(AppAssets.spacing.screenSpacing),
                verticalArrangement = Arrangement.spacedBy(AppAssets.spacing.verticalItemSpacing)
            ) {
                items(
                    items = uiState.subjects,
                ) { subject ->
                    SubjectComponent(
                        subject = subject,
                        onClick = {
                            onUiEvent.invoke(
                                SubjectOverviewUiEvent.SubjectClick(subject = subject)
                            )
                        },
                        onLongClick = {
                            onUiEvent.invoke(
                                SubjectOverviewUiEvent.SubjectLongClick(subject = subject)
                            )                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        } else {
            NoSubjectsIndicator(
                onButtonCreateFirstSubjectClick = {
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

@PreviewLightDark
@Composable
private fun PreviewSubjectsOverviewScreen() {
    GradeManagerTheme {
        SubjectsOverviewScreen(
            uiState = SubjectOverviewUiState(
                subjects = listOf(SubjectMock)
            ),
            onUiEvent = {}
        )
    }
}
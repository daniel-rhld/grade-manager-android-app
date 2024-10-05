package de.grademanager.feature.subjects.presentation.detail

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
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import de.grademanager.core.presentation.components.DefaultTopAppBar
import de.grademanager.core.presentation.theme.AppAssets
import de.grademanager.feature.subjects.presentation.detail.components.GradeComponent
import de.grademanager.feature.subjects.presentation.detail.components.NoGradesIndicator
import org.koin.androidx.compose.koinViewModel

@Destination<RootGraph>
@Composable
fun SubjectDetailScreen(
    subjectId: Int,
    subjectName: String,
    navigator: DestinationsNavigator
) {
    val viewModel: SubjectDetailViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsState()

    SubjectDetailScreen(
        uiState = uiState,
        onUiEvent = { event ->
            when (event) {
                is SubjectDetailUiEvent.NavigationIconClick -> {
                    navigator.navigateUp()
                }

                is SubjectDetailUiEvent.GradeClick -> {
                    // TODO: Navigate to grade
                }

                else -> viewModel.onUiEvent(event = event)
            }
        }
    )
}

@Composable
private fun SubjectDetailScreen(
    uiState: SubjectDetailUiState,
    onUiEvent: (SubjectDetailUiEvent) -> Unit
) {
    Scaffold(
        topBar = {
            DefaultTopAppBar(
                title = uiState.subjectName,
                withNavigationIcon = true,
                onNavigationIconClick = {
                    onUiEvent.invoke(
                        SubjectDetailUiEvent.NavigationIconClick
                    )
                }
            )
        },
        floatingActionButton = {
            if (uiState.grades.isNotEmpty()) {
                FloatingActionButton(
                    onClick = {
                        onUiEvent.invoke(
                            SubjectDetailUiEvent.ButtonAddGradeClick
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
    ) { padding ->
        if (uiState.grades.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(padding),
                contentPadding = PaddingValues(
                    top = AppAssets.spacing.screenSpacing,
                    start = AppAssets.spacing.screenSpacing,
                    end = AppAssets.spacing.screenSpacing,
                    bottom = AppAssets.scaffoldWithFabBottomPadding
                )
            ) {
                items(
                    items = uiState.grades,
                ) { grade ->
                    GradeComponent(
                        grade = grade,
                        onClick = {
                            onUiEvent.invoke(
                                SubjectDetailUiEvent.GradeClick(
                                    grade = grade
                                )
                            )
                        },
                        onDeleteRequested = {
                            onUiEvent.invoke(
                                SubjectDetailUiEvent.GradeDeleteRequested(
                                    grade = grade
                                )
                            )
                        },
                        horizontalPadding = AppAssets.spacing.screenSpacing,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        } else {
            NoGradesIndicator(
                onButtonCreateFirstGradeClick = {
                    onUiEvent.invoke(
                        SubjectDetailUiEvent.ButtonAddFirstGradeClick
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
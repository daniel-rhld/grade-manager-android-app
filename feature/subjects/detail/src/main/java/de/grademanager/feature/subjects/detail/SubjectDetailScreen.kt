package de.grademanager.feature.subjects.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import de.grademanager.core.designsystem.components.AppSnackbarHost
import de.grademanager.core.designsystem.components.GradeManagerTopAppBar
import de.grademanager.core.designsystem.icons.GradeManagerIcons
import de.grademanager.core.designsystem.theme.AppAssets
import de.grademanager.core.designsystem.theme.GradeManagerTheme
import de.grademanager.core.domain.controller.snackbar.SnackbarController
import de.grademanager.core.mock.ModelMock
import de.grademanager.core.model.GradeOrdering
import de.grademanager.core.model.OrderingDirection
import de.grademanager.core.ui.BottomAverageGradeComponent
import de.grademanager.core.ui.GradeComponent
import de.grademanager.core.ui.NoItemsIndicator
import de.grademanager.core.ui.extensions.collectAsState
import de.grademanager.feature.grades.add.AddGradeDialog
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject
import java.util.Calendar
import java.util.Date

@Serializable
data class SubjectDetailScreenDestination(
    val subjectId: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectDetailScreen(
    subjectId: Int,
    navController: NavController
) {
    val viewModel: SubjectDetailViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsState()

    val addGradeDialogVisible by viewModel.addGradeDialogVisible.collectAsState()
    val addGradeDialogUiState by viewModel.addGradeDialogUiState.collectAsState()
    val datePickerForNewGradeVisible by viewModel.datePickerForNewGradeVisible.collectAsState()

    val changeGradeOrderingDialogVisible by viewModel.changeGradeOrderingDialogVisible.collectAsState()

    val datePickerForNewGradeState = rememberDatePickerState(
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                val today = Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, 23)
                    set(Calendar.MINUTE, 59)
                    set(Calendar.SECOND, 59)
                    set(Calendar.MILLISECOND, 999)
                }.time.time

                return utcTimeMillis <= today
            }
        }
    )

    if (datePickerForNewGradeVisible) {
        DatePickerDialog(
            onDismissRequest = viewModel::dismissDatePickerForNewGrade,
            confirmButton = {
                Button(
                    onClick = {
                        datePickerForNewGradeState.selectedDateMillis?.let(::Date)?.let { selectedDate ->
                            viewModel.updateReceivedAtForNewGrade(selectedDate)
                            viewModel.dismissDatePickerForNewGrade()
                        }
                    }
                ) {
                    Icon(
                        imageVector = GradeManagerIcons.Check,
                        contentDescription = null
                    )

                    Spacer(
                        modifier = Modifier.width(8.dp)
                    )

                    Text(
                        text = stringResource(
                            de.grademanager.core.common.R.string.core_common_date_picker_button_save
                        )
                    )
                }
            }
        ) {
            DatePicker(
                state = datePickerForNewGradeState
            )
        }
    }

    if (addGradeDialogVisible) {
        AddGradeDialog(
            uiState = addGradeDialogUiState,
            onUiEvent = viewModel::onUiEvent
        )
    }

    if (changeGradeOrderingDialogVisible) {
        ChangeGradeOrderingDialog(
            currentOrdering = uiState.gradeOrdering,
            onOrderingSelected = viewModel::changeGradeOrdering,
            onDismissRequested = viewModel::dismissChangeGradeOrderingDialog
        )
    }

    SubjectDetailScreen(
        uiState = uiState,
        onUiEvent = { event ->
            when (event) {
                is SubjectDetailUiEvent.NavigationIconClick -> {
                    navController.popBackStack()
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
            GradeManagerTopAppBar(
                title = uiState.subjectName,
                withNavigationIcon = true,
                onNavigationIconClick = {
                    onUiEvent.invoke(
                        SubjectDetailUiEvent.NavigationIconClick
                    )
                },
                actions = {
                    if (uiState.grades.isNotEmpty()) {
                        IconButton(
                            onClick = {
                                onUiEvent.invoke(
                                    SubjectDetailUiEvent.ChangeGradeOrderingRequested
                                )
                            }
                        ) {
                            Icon(
                                imageVector = GradeManagerIcons.Sort,
                                contentDescription = null
                            )
                        }
                    }
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
                    },
                    modifier = Modifier.padding(bottom = AppAssets.sizes.bottomGradeAverageComponentHeight)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = null
                    )
                }
            }
        },
        snackbarHost = {
            AppSnackbarHost()
        }
    ) { padding ->
        if (uiState.grades.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1F),
                    contentPadding = PaddingValues(
                        top = AppAssets.spacing.screenSpacing,
                        bottom = AppAssets.scaffoldWithFabBottomPadding
                    ),
                    verticalArrangement = Arrangement.spacedBy(AppAssets.spacing.verticalItemSpacing)
                ) {
                    items(
                        items = uiState.grades,
                        key = { it.id }
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
                            modifier = Modifier
                                .fillMaxWidth()
                                .animateItem()
                        )
                    }
                }

                BottomAverageGradeComponent(
                    label = stringResource(R.string.feature_subjects_detail_grade_average_label),
                    grade = uiState.averageGrade
                )
            }
        } else {
            NoItemsIndicator(
                label = stringResource(R.string.feature_subjects_detail_no_grades_created_label),
                actionLabel = stringResource(R.string.feature_subjects_detail_button_create_first_grade),
                onActionClick = {
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

@PreviewLightDark
@Composable
private fun PreviewSubjectDetailScreen() {
    GradeManagerTheme {
        SubjectDetailScreen(
            uiState = SubjectDetailUiState(
                subjectName = "Mathematik",
                grades = listOf(ModelMock.Grade),
                averageGrade = 1.0,
                gradeOrdering = GradeOrdering.Value(OrderingDirection.DESCENDING),
            ),
            onUiEvent = {}
        )
    }
}
package de.grademanager.feature.subjects.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.navArgument
import androidx.navigation.toRoute
import de.grademanager.common.util.State
import de.grademanager.common.util.asStringWrapper
import de.grademanager.common.util.fold
import de.grademanager.core.descriptors.SnackbarType
import de.grademanager.core.domain.controller.snackbar.SnackbarController
import de.grademanager.core.domain.use_case.calculate_average_grade.CalculateAverageGradeUseCase
import de.grademanager.core.domain.use_case.grade_create.CreateGradeUseCase
import de.grademanager.core.domain.use_case.grade_delete.DeleteGradeUseCase
import de.grademanager.core.domain.use_case.grade_get_for_subject.GetGradesForSubjectUseCase
import de.grademanager.core.domain.use_case.grade_ordering_get.GetGradeOrderingUseCase
import de.grademanager.core.domain.use_case.grade_ordering_update.UpdateGradeOrderingUseCase
import de.grademanager.core.domain.use_case.grade_restore.RestoreGradeUseCase
import de.grademanager.core.domain.use_case.subject_find_by_id.FindSubjectByIdUseCase
import de.grademanager.core.model.GradeOrdering
import de.grademanager.core.model.OrderingDirection
import de.grademanager.feature.grades.add.AddGradeDialogUiEvent
import de.grademanager.feature.grades.add.AddGradeDialogUiState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Date

class SubjectDetailViewModel(
    private val findSubjectByIdUseCase: FindSubjectByIdUseCase,

    private val getAllGradesForSubjectUseCase: GetGradesForSubjectUseCase,

    private val createGradeUseCase: CreateGradeUseCase,
    private val deleteGradeUseCase: DeleteGradeUseCase,
    private val restoreGradeUseCase: RestoreGradeUseCase,

    private val getGradeOrderingUseCase: GetGradeOrderingUseCase,
    private val updateGradeOrderingUseCase: UpdateGradeOrderingUseCase,

    private val calculateAverageGradeUseCase: CalculateAverageGradeUseCase,

    private val snackbarController: SnackbarController,

    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val navArgs = savedStateHandle.toRoute<SubjectDetailScreenDestination>()

    val uiState = State(
        SubjectDetailUiState(
            subjectName = navArgs.subjectName,
            grades = emptyList(),
            averageGrade = 1.0,
            gradeOrdering = GradeOrdering.Value(direction = OrderingDirection.DESCENDING)
        )
    )

    val addGradeDialogVisible = State(false)
    val addGradeDialogUiState = State(
        AddGradeDialogUiState(
            grade = "",
            weighting = AddGradeDialogUiState.GradeWeighting.ONE,
            description = "",
            receivedAt = Date(),
            buttonSaveEnabled = false
        )
    )
    val datePickerForNewGradeVisible = State(false)

    val changeGradeOrderingDialogVisible = State(false)

    init {
        viewModelScope.launch {
            findSubjectByIdUseCase.findSubjectByIdAsFlow(
                id = navArgs.subjectId
            ).collectLatest { subject ->
                subject?.let {
                    uiState.update {
                        it.copy(subjectName = subject.name)
                    }
                }
            }
        }

        viewModelScope.launch {
            getGradeOrderingUseCase.invoke().collectLatest { gradeOrdering ->
                getAllGradesForSubjectUseCase.invoke(
                    subjectId = navArgs.subjectId,
                    gradeOrdering = gradeOrdering
                ).collectLatest { grades ->
                    uiState.update {
                        it.copy(
                            grades = grades,
                            averageGrade = calculateAverageGradeUseCase.calculateAverageGrade(
                                grades = grades
                            ),
                            gradeOrdering = gradeOrdering
                        )
                    }
                }
            }
        }

        viewModelScope.launch {
            addGradeDialogUiState.collectLatest { uiState ->
                addGradeDialogUiState.update {
                    val gradeValue = try {
                        uiState.grade.replace(",", ".").toDouble()
                    } catch (e: NumberFormatException) {
                        null
                    }

                    it.copy(
                        buttonSaveEnabled = gradeValue != null && gradeValue >= 1.0 && gradeValue <= 6.0
                    )
                }
            }
        }
    }

    fun onUiEvent(event: SubjectDetailUiEvent) {
        when (event) {
            is SubjectDetailUiEvent.ButtonAddFirstGradeClick,
            is SubjectDetailUiEvent.ButtonAddGradeClick -> {
                addGradeDialogVisible.update(true)
            }

            is SubjectDetailUiEvent.GradeDeleteRequested -> {
                viewModelScope.launch {
                    deleteGradeUseCase.invoke(gradeId = event.grade.id).let { result ->
                        result.fold(
                            onSuccess = {
                                snackbarController.showSnackbar {
                                    type = SnackbarType.INFO
                                    message = R.string.feature_subjects_detail_grade_deleted.asStringWrapper()

                                    action {
                                        label = R.string.feature_subjects_detail_grade_deleted_revert.asStringWrapper()
                                        action = {
                                            restoreGradeUseCase.invoke(gradeId = event.grade.id)
                                        }
                                    }
                                }
                            },
                            onFailure = { error ->
                                error?.let { errorMessage ->
                                    snackbarController.showSnackbar {
                                        type = SnackbarType.ERROR
                                        message = errorMessage
                                    }
                                }
                            }
                        )
                    }
                }
            }

            is SubjectDetailUiEvent.ChangeGradeOrderingRequested -> {
                changeGradeOrderingDialogVisible.update(true)
            }

            else -> Unit
        }
    }

    fun onUiEvent(event: AddGradeDialogUiEvent) {
        when (event) {
            is AddGradeDialogUiEvent.DismissRequested -> {
                addGradeDialogVisible.update(false)
            }

            is AddGradeDialogUiEvent.GradeChanged -> {
                addGradeDialogUiState.update {
                    it.copy(
                        grade = event.value
                    )
                }
            }

            is AddGradeDialogUiEvent.WeightingChanged -> {
                AddGradeDialogUiState.GradeWeighting.findByScale(
                    value = event.value
                )?.let { weighting ->
                    addGradeDialogUiState.update {
                        it.copy(
                            weighting = weighting
                        )
                    }
                }
            }

            is AddGradeDialogUiEvent.DescriptionChanged -> {
                addGradeDialogUiState.update {
                    it.copy(
                        description = event.value
                    )
                }
            }

            is AddGradeDialogUiEvent.ChangeReceivedAtRequested -> {
                datePickerForNewGradeVisible.update(true)
            }

            is AddGradeDialogUiEvent.ButtonSaveClick -> {
                addGradeDialogUiState.currentValue().let { addGradeUiState ->
                    viewModelScope.launch {
                        createGradeUseCase.invoke(
                            grade = addGradeUiState.grade,
                            weighting = addGradeUiState.weighting.weighting,
                            description = addGradeUiState.description,
                            receivedAt = addGradeUiState.receivedAt,
                            subjectId = navArgs.subjectId
                        ).let { result ->
                            addGradeDialogVisible.update(false)

                            result.fold(
                                onSuccess = {
                                    addGradeDialogUiState.update {
                                        it.copy(
                                            grade = "",
                                            weighting = AddGradeDialogUiState.GradeWeighting.ONE,
                                            description = "",
                                            receivedAt = Date()
                                        )
                                    }
                                },
                                onFailure = { error ->
                                    error?.let { errorMessage ->
                                        snackbarController.showSnackbar {
                                            type = SnackbarType.ERROR
                                            message = errorMessage
                                        }
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    fun updateReceivedAtForNewGrade(value: Date) {
        addGradeDialogUiState.update {
            it.copy(
                receivedAt = value
            )
        }
    }

    fun dismissDatePickerForNewGrade() {
        datePickerForNewGradeVisible.update(false)
    }

    fun changeGradeOrdering(value: GradeOrdering) {
        viewModelScope.launch {
            updateGradeOrderingUseCase.invoke(value = value)
        }
    }

    fun dismissChangeGradeOrderingDialog() {
        changeGradeOrderingDialogVisible.update(false)
    }

}
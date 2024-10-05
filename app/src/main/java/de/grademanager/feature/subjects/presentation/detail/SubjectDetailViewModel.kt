package de.grademanager.feature.subjects.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramcosta.composedestinations.generated.destinations.SubjectDetailScreenDestination
import de.grademanager.core.data.model.State
import de.grademanager.core.data.model.fold
import de.grademanager.core.presentation.snackbar.SnackbarController
import de.grademanager.feature.grades.domain.use_case.CreateGradeUseCase
import de.grademanager.feature.grades.domain.use_case.GetAllGradesForSubjectUseCase
import de.grademanager.feature.grades.presentation.add_grade.AddGradeDialogUiEvent
import de.grademanager.feature.grades.presentation.add_grade.AddGradeDialogUiState
import de.grademanager.feature.subjects.domain.models.GradeOrdering
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Date

class SubjectDetailViewModel(
    private val getAllGradesForSubjectUseCase: GetAllGradesForSubjectUseCase,
    private val createGradeUseCase: CreateGradeUseCase,

    private val snackbarController: SnackbarController,

    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val navArgs = SubjectDetailScreenDestination.argsFrom(savedStateHandle)

    val uiState = State(
        SubjectDetailUiState(
            subjectName = navArgs.subjectName,
            grades = emptyList(),
            gradeOrdering = GradeOrdering.Value(ascending = false)
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

    init {
        viewModelScope.launch {
            getAllGradesForSubjectUseCase.invoke(
                subjectId = navArgs.subjectId
            ).collectLatest { grades ->
                uiState.update {
                    it.copy(
                        grades = grades
                    )
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
                // TODO: Delete grade
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
                                    error?.let {
                                        viewModelScope.launch {
                                            snackbarController.showErrorSnackbar(it)
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

}
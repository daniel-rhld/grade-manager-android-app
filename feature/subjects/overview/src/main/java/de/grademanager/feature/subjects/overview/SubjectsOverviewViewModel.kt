package de.grademanager.feature.subjects.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.grademanager.common.util.State
import de.grademanager.common.util.fold
import de.grademanager.core.domain.use_case.calculate_average_grade.CalculateAverageGradeUseCase
import de.grademanager.core.domain.use_case.subject_create.CreateSubjectUseCase
import de.grademanager.core.domain.use_case.subject_get_ordered.GetSubjectsOrderedUseCase
import de.grademanager.core.domain.use_case.subject_update.UpdateSubjectUseCase
import de.grademanager.core.model.Subject
import de.grademanager.core.model.SubjectWithAverageGrade
import de.grademanager.feature.subjects.manage.ManageSubjectDialogUiEvent
import de.grademanager.feature.subjects.manage.ManageSubjectDialogUiState
import de.grademanager.feature.subjects.manage.ManageSubjectMode
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class SubjectsOverviewViewModel(
    private val getSubjectsOrderedUseCase: GetSubjectsOrderedUseCase,

    private val createSubjectUseCase: CreateSubjectUseCase,
    private val updateSubjectUseCase: UpdateSubjectUseCase,

    private val calculateAverageSubjectGradeUseCase: CalculateAverageGradeUseCase
) : ViewModel() {

    val uiState = State(
        SubjectOverviewUiState(
            subjects = emptyList(),
            overallGradesVisible = false,
            averageGrade = 1.0
        )
    )

    val createSubjectDialogVisible = State(false)
    val createSubjectDialogUiState = State(
        ManageSubjectDialogUiState(
            mode = ManageSubjectMode.Create,
            name = "",
            nameError = null,
            buttonSaveEnabled = false
        )
    )

    val updateSubjectDialogVisible = State(false)
    val updateSubjectDialogUiState = State(
        ManageSubjectDialogUiState(
            mode = ManageSubjectMode.Update,
            name = "",
            nameError = null,
            buttonSaveEnabled = false
        )
    )
    private var subjectToEdit = State<Subject?>(null)

    init {
        viewModelScope.launch {
            getSubjectsOrderedUseCase.invoke(
                orderColumn = "name",
                orderAscending = true
            ).collectLatest { subjects ->
                val hasAnyGrades = subjects.map {
                    it.grades
                }.flatten().isNotEmpty()

                uiState.update {
                    it.copy(
                        subjects = subjects.map { subject ->
                            SubjectWithAverageGrade(
                                self = subject,
                                averageGrade = calculateAverageSubjectGradeUseCase.calculateAverageSubjectGrade(
                                    subject = subject
                                )
                            )
                        },
                        overallGradesVisible = hasAnyGrades,
                        averageGrade = if (hasAnyGrades) {
                            calculateAverageSubjectGradeUseCase.calculateAverageSubjectGrade(
                                subjects = subjects
                            )
                        } else {
                            0.0
                        }
                    )
                }
            }
        }

        viewModelScope.launch {
            createSubjectDialogUiState.collectLatest { uiState ->
                createSubjectDialogUiState.update {
                    it.copy(
                        name = uiState.name,
                        buttonSaveEnabled = uiState.name.isNotBlank()
                    )
                }
            }
        }

        viewModelScope.launch {
            combine(subjectToEdit.state, updateSubjectDialogUiState.state) { subjectToEdit, subject ->
                updateSubjectDialogUiState.update {
                    it.copy(
                        name = subject.name,
                        buttonSaveEnabled = subject.name.isNotBlank() && subject.name.trim() != subjectToEdit?.name?.trim()
                    )
                }
            }.collect()
        }
    }

    fun onUiEvent(event: SubjectOverviewUiEvent) {
        when (event) {
            is SubjectOverviewUiEvent.ButtonCreateFirstSubjectClick,
            is SubjectOverviewUiEvent.ButtonAddSubjectClick -> {
                createSubjectDialogVisible.update(true)
            }

            is SubjectOverviewUiEvent.SubjectLongClick -> {
                subjectToEdit.update(event.subject)
                updateSubjectDialogVisible.update(true)
                updateSubjectDialogUiState.update {
                    it.copy(
                        name = event.subject.name,
                    )
                }
            }

            else -> Unit
        }
    }

    fun onCreateSubjectDialogUiEvent(event: ManageSubjectDialogUiEvent) {
        when (event) {
            is ManageSubjectDialogUiEvent.DismissRequested -> {
                createSubjectDialogVisible.update(false)
                createSubjectDialogUiState.update {
                    it.copy(
                        nameError = null
                    )
                }
            }

            is ManageSubjectDialogUiEvent.NameChange -> {
                createSubjectDialogUiState.update {
                    it.copy(
                        name = event.value
                    )
                }
            }

            is ManageSubjectDialogUiEvent.ButtonSaveClick -> {
                viewModelScope.launch {
                    createSubjectUseCase.invoke(
                        name = createSubjectDialogUiState.currentValue().name
                    ).let { result ->
                        result.fold(
                            onSuccess = {
                                createSubjectDialogVisible.update(false)
                                createSubjectDialogUiState.update {
                                    it.copy(
                                        name = "",
                                        nameError = null
                                    )
                                }
                            },
                            onFailure = { error ->
                                createSubjectDialogUiState.update {
                                    it.copy(
                                        nameError = error
                                    )
                                }
                            }
                        )
                    }
                }
            }
        }
    }

    fun onUpdateSubjectDialogUiEvent(event: ManageSubjectDialogUiEvent) {
        when (event) {
            is ManageSubjectDialogUiEvent.DismissRequested -> {
                updateSubjectDialogVisible.update(false)
                updateSubjectDialogUiState.update {
                    it.copy(
                        nameError = null
                    )
                }
            }

            is ManageSubjectDialogUiEvent.NameChange -> {
                updateSubjectDialogUiState.update {
                    it.copy(
                        name = event.value
                    )
                }
            }

            is ManageSubjectDialogUiEvent.ButtonSaveClick -> {
                val id = subjectToEdit.currentValue()?.id
                val name = updateSubjectDialogUiState.currentValue().name

                if (id != null) {
                    viewModelScope.launch {
                        updateSubjectUseCase.invoke(
                            id = id,
                            name = name
                        ).let { result ->
                            result.fold(
                                onSuccess = {
                                    updateSubjectDialogVisible.update(false)
                                    updateSubjectDialogUiState.update {
                                        it.copy(
                                            name = "",
                                            nameError = null
                                        )
                                    }
                                },
                                onFailure = { error ->
                                    updateSubjectDialogUiState.update {
                                        it.copy(
                                            nameError = error
                                        )
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }

}
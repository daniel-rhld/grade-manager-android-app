package de.grademanager.feature.subjects.presentation.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.grademanager.core.data.model.State
import de.grademanager.core.data.model.fold
import de.grademanager.feature.subjects.domain.model.Subject
import de.grademanager.feature.subjects.domain.model.SubjectWitAverageGrade
import de.grademanager.feature.subjects.domain.use_case.calculate_average_subject_grade.CalculateAverageSubjectGradeUseCase
import de.grademanager.feature.subjects.domain.use_case.create_subject.CreateSubjectUseCase
import de.grademanager.feature.subjects.domain.use_case.get_existing_subjects_ordered.GetExistingSubjectsOrderedUseCase
import de.grademanager.feature.subjects.domain.use_case.update_subject.UpdateSubjectUseCase
import de.grademanager.feature.subjects.presentation.manage_subject.ManageSubjectDialogUiEvent
import de.grademanager.feature.subjects.presentation.manage_subject.ManageSubjectDialogUiState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class SubjectsOverviewViewModel(
    private val getExistingSubjectsOrdered: GetExistingSubjectsOrderedUseCase,

    private val createSubjectUseCase: CreateSubjectUseCase,
    private val updateSubjectUseCase: UpdateSubjectUseCase,

    private val calculateAverageSubjectGradeUseCase: CalculateAverageSubjectGradeUseCase
) : ViewModel() {

    val uiState = State(
        SubjectOverviewUiState(
            subjects = emptyList(),
            averageGrade = 1.0
        )
    )

    val createSubjectDialogVisible = State(false)
    val createSubjectDialogUiState = State(
        ManageSubjectDialogUiState(
            name = "",
            nameError = null,
            buttonSaveEnabled = false
        )
    )

    val updateSubjectDialogVisible = State(false)
    val updateSubjectDialogUiState = State(
        ManageSubjectDialogUiState(
            name = "",
            nameError = null,
            buttonSaveEnabled = false
        )
    )
    private var subjectToEdit = State<Subject?>(null)

    init {
        viewModelScope.launch {
            getExistingSubjectsOrdered.invoke(
                orderColumn = "name",
                orderAscending = true
            ).collectLatest { subjects ->
                uiState.update {
                    it.copy(
                        subjects = subjects.map { subject ->
                            SubjectWitAverageGrade(
                                self = subject,
                                averageGrade = calculateAverageSubjectGradeUseCase.calculateAverageGrade(
                                    subject = subject
                                )
                            )
                        },
                        averageGrade = calculateAverageSubjectGradeUseCase.calculateAverageGrade(
                            subjects = subjects
                        )
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
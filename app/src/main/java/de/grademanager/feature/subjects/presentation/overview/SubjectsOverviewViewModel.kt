package de.grademanager.feature.subjects.presentation.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.grademanager.core.data.model.DataResult
import de.grademanager.core.data.model.State
import de.grademanager.feature.subjects.domain.models.Subject
import de.grademanager.feature.subjects.domain.use_cases.CreateSubjectUseCase
import de.grademanager.feature.subjects.domain.use_cases.GetExistingSubjectsOrdered
import de.grademanager.feature.subjects.domain.use_cases.UpdateSubjectUseCase
import de.grademanager.feature.subjects.presentation.manage_subject.ManageSubjectDialogUiEvent
import de.grademanager.feature.subjects.presentation.manage_subject.ManageSubjectDialogUiState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class SubjectsOverviewViewModel(
    private val getExistingSubjectsOrdered: GetExistingSubjectsOrdered,
    private val createSubjectUseCase: CreateSubjectUseCase,
    private val updateSubjectUseCase: UpdateSubjectUseCase
) : ViewModel() {

    val uiState = State(
        SubjectOverviewUiState(
            subjects = emptyList()
        )
    )

    val createSubjectDialogVisible = State(false)
    private val createSubjectDialogName = State("")
    val createSubjectDialogUiState = State(
        ManageSubjectDialogUiState(
            name = "",
            buttonSaveEnabled = false
        )
    )

    val updateSubjectDialogVisible = State(false)
    private val updateSubjectDialogName = State("")
    val updateSubjectDialogUiState = State(
        ManageSubjectDialogUiState(
            name = "",
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
                        subjects = subjects
                    )
                }
            }
        }

        viewModelScope.launch {
            createSubjectDialogName.collectLatest { subjectName ->
                createSubjectDialogUiState.update {
                    it.copy(
                        name = subjectName,
                        buttonSaveEnabled = subjectName.isNotBlank()
                    )
                }
            }
        }

        viewModelScope.launch {
            combine(subjectToEdit.state, updateSubjectDialogName.state) { subject, subjectName ->
                updateSubjectDialogUiState.update {
                    it.copy(
                        name = subjectName,
                        buttonSaveEnabled = subjectName.isNotBlank() && subjectName.trim() != subject?.name?.trim()
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
                updateSubjectDialogName.update(event.subject.name)
                updateSubjectDialogVisible.update(true)
            }

            else -> Unit
        }
    }

    fun onCreateSubjectDialogUiEvent(event: ManageSubjectDialogUiEvent) {
        when (event) {
            is ManageSubjectDialogUiEvent.DismissRequested -> {
                createSubjectDialogVisible.update(false)
            }

            is ManageSubjectDialogUiEvent.NameChange -> {
                createSubjectDialogName.update(event.value)
            }

            is ManageSubjectDialogUiEvent.ButtonSaveClick -> {
                viewModelScope.launch {
                    createSubjectUseCase.invoke(
                        name = createSubjectDialogUiState.currentValue().name
                    ).let { result ->
                        if (result is DataResult.Success) {
                            createSubjectDialogName.update("")
                            createSubjectDialogVisible.update(false)
                        } else {
                            // TODO: Add error handling
                        }
                    }
                }
            }
        }
    }

    fun onUpdateSubjectDialogUiEvent(event: ManageSubjectDialogUiEvent) {
        when (event) {
            is ManageSubjectDialogUiEvent.DismissRequested -> {
                updateSubjectDialogVisible.update(false)
            }

            is ManageSubjectDialogUiEvent.NameChange -> {
                updateSubjectDialogName.update(event.value)
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
                            if (result is DataResult.Success) {
                                updateSubjectDialogName.update("")
                                updateSubjectDialogVisible.update(false)
                            } else {
                                // TODO: Add error handling
                            }
                        }
                    }
                }
            }
        }
    }

}
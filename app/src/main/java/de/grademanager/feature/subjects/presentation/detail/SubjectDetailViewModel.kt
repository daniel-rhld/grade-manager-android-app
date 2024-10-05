package de.grademanager.feature.subjects.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.ramcosta.composedestinations.generated.destinations.SubjectDetailScreenDestination
import de.grademanager.core.data.model.State
import de.grademanager.feature.subjects.domain.models.GradeOrdering

class SubjectDetailViewModel(
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

    fun onUiEvent(event: SubjectDetailUiEvent) {
        when (event) {
            is SubjectDetailUiEvent.ButtonAddFirstGradeClick,
            is SubjectDetailUiEvent.ButtonAddGradeClick -> {
                // TODO: Add grade
            }

            is SubjectDetailUiEvent.GradeDeleteRequested -> {
                // TODO: Delete grade
            }

            else -> Unit
        }
    }

}
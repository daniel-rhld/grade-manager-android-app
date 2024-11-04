package de.grademanager.feature.grades.add

sealed class AddGradeDialogUiEvent {
    data object DismissRequested : AddGradeDialogUiEvent()

    data class GradeChanged(val value: String) : AddGradeDialogUiEvent()
    data class WeightingChanged(val value: Double) : AddGradeDialogUiEvent()
    data object ChangeReceivedAtRequested : AddGradeDialogUiEvent()
    data class DescriptionChanged(val value: String) : AddGradeDialogUiEvent()

    data object ButtonSaveClick : AddGradeDialogUiEvent()
}
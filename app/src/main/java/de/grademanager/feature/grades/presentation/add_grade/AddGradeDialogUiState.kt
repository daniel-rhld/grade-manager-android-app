package de.grademanager.feature.grades.presentation.add_grade

import java.util.Date

data class AddGradeDialogUiState(
    val grade: String,
    val weighting: GradeWeighting,
    val description: String,
    val receivedAt: Date,
    val buttonSaveEnabled: Boolean
) {
    enum class GradeWeighting(
        val weighting: Double,
        val onScale: Double
    ) {
        QUARTER                 (weighting = 0.25, onScale = 0.0),
        THIRD                   (weighting = 0.33, onScale = 0.1),
        HALF                    (weighting = 0.50, onScale = 0.2),
        THREE_QUARTERS          (weighting = 0.75, onScale = 0.3),
        ONE                     (weighting = 1.00, onScale = 0.4),
        ONE_AND_QUARTER         (weighting = 1.25, onScale = 0.6),
        ONE_AND_THIRD           (weighting = 1.33, onScale = 0.7),
        ONE_AND_HALF            (weighting = 1.50, onScale = 0.8),
        ONE_AND_THREE_QUARTERS  (weighting = 1.75, onScale = 0.9),
        TWO                     (weighting = 2.00, onScale = 1.0);

        companion object {
            fun findByScale(value: Double): GradeWeighting? {
                return entries.find { it.onScale == value }
            }
        }
    }
}

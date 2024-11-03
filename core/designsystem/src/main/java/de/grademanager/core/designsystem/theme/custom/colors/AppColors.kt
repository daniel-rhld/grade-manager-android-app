package de.grademanager.core.presentation.theme.custom.colors

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class AppColors(
    gradeStatusColors: GradeStatusColors
) {
    companion object

    var gradeStatusColors by mutableStateOf(gradeStatusColors)
        private set

    fun update(other: AppColors) {
        other.gradeStatusColors.update(other.gradeStatusColors)
    }
}

val AppColors.Companion.lightColorScheme: AppColors
    get() = AppColors(
        gradeStatusColors = GradeStatusColors.lightColorScheme
    )

val AppColors.Companion.darkColorScheme: AppColors
    get() = AppColors(
        gradeStatusColors = GradeStatusColors.darkColorScheme
    )
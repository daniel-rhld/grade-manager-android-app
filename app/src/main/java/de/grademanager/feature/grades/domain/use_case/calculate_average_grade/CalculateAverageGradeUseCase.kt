package de.grademanager.feature.grades.domain.use_case.calculate_average_grade

import de.grademanager.feature.grades.domain.model.Grade

interface CalculateAverageGradeUseCase {

    operator fun invoke(grades: List<Grade>): Double

}
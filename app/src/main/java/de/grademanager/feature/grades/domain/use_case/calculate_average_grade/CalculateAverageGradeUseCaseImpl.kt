package de.grademanager.feature.grades.domain.use_case.calculate_average_grade

import de.grademanager.feature.grades.domain.model.Grade
import kotlin.math.max

class CalculateAverageGradeUseCaseImpl : CalculateAverageGradeUseCase {

    override fun invoke(grades: List<Grade>): Double {
        return grades.filter { it.deletedAt == null }
            .sumOf { it.grade * it.weighting }.div(
                other = max(
                    a = grades.sumOf { it.weighting },
                    b = 1.0
                )
            )
    }

}
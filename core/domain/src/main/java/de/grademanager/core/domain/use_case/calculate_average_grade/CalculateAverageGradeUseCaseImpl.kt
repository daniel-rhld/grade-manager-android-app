package de.grademanager.core.domain.use_case.calculate_average_grade

import de.grademanager.core.model.Grade
import de.grademanager.core.model.Subject
import kotlin.math.max

class CalculateAverageGradeUseCaseImpl : CalculateAverageGradeUseCase {

    override fun calculateAverageGrade(grades: List<Grade>): Double {
        return grades.filter { it.deletedAt == null }
            .sumOf { it.grade * it.weighting }.div(
                other = max(
                    a = grades.sumOf { it.weighting },
                    b = 1.0
                )
            )
    }

    override fun calculateAverageSubjectGrade(subject: Subject): Double {
        return calculateAverageGrade(subject.grades)
    }

    override fun calculateAverageSubjectGrade(subjects: List<Subject>): Double {
        return subjects.sumOf { subject ->
            calculateAverageSubjectGrade(subject)
        }.div(
            max(
                a = subjects.filter { it.grades.isNotEmpty() }.size,
                b = 1
            )
        )
    }

}
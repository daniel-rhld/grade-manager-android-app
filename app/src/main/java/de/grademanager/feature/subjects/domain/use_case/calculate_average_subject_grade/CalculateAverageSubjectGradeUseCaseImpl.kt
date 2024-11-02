package de.grademanager.feature.subjects.domain.use_case.calculate_average_subject_grade

import de.grademanager.feature.subjects.domain.model.Subject
import de.grademanager.feature.subjects.domain.model.hasAnyGrades
import kotlin.math.max

class CalculateAverageSubjectGradeUseCaseImpl : CalculateAverageSubjectGradeUseCase {

    override fun calculateAverageGrade(subject: Subject): Double {
        return subject.grades.filter { it.deletedAt == null }
            .sumOf { it.grade * it.weighting }.div(
                other = max(
                    a = subject.grades.sumOf { it.weighting },
                    b = 1.0
                )
            )
    }

    override fun calculateAverageGrade(subjects: List<Subject>): Double {
        return subjects.sumOf { subject ->
            calculateAverageGrade(subject)
        }.div(
            max(
                a = subjects.filter { it.hasAnyGrades() }.size,
                b = 1
            )
        )
    }

}
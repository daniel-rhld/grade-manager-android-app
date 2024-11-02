package de.grademanager.feature.subjects.domain.use_case.calculate_average_subject_grade

import de.grademanager.feature.subjects.domain.model.Subject

interface CalculateAverageSubjectGradeUseCase {

    fun calculateAverageGrade(subject: Subject): Double

    fun calculateAverageGrade(subjects: List<Subject>): Double

}
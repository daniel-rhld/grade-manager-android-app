package de.grademanager.core.domain.use_case.calculate_average_grade

import de.grademanager.core.model.Grade
import de.grademanager.core.model.Subject

interface CalculateAverageGradeUseCase {

    fun calculateAverageGrade(grades: List<Grade>): Double

    fun calculateAverageSubjectGrade(subject: Subject): Double

    fun calculateAverageSubjectGrade(subjects: List<Subject>): Double

}
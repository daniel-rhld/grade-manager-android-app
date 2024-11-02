package de.grademanager.feature.grades.domain.use_case.update_grade_ordering

import de.grademanager.feature.subjects.domain.model.GradeOrdering

interface UpdateGradeOrderingUseCase {

    suspend operator fun invoke(value: GradeOrdering)

}
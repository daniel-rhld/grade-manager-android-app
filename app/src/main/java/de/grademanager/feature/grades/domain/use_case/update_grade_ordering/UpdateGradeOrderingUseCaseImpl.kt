package de.grademanager.feature.grades.domain.use_case.update_grade_ordering

import de.grademanager.feature.grades.domain.repository.GradeRepository
import de.grademanager.feature.subjects.domain.model.GradeOrdering

class UpdateGradeOrderingUseCaseImpl(
    private val gradeRepository: GradeRepository
) : UpdateGradeOrderingUseCase {

    override suspend operator fun invoke(value: GradeOrdering) {
        gradeRepository.updateGradeOrdering(value)
    }

}
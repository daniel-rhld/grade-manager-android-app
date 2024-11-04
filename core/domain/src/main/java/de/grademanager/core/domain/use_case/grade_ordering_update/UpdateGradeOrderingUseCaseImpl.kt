package de.grademanager.core.domain.use_case.grade_ordering_update

import de.grademanager.core.data.repository.grade.GradeRepository
import de.grademanager.core.model.GradeOrdering

class UpdateGradeOrderingUseCaseImpl(
    private val gradeRepository: GradeRepository
) : UpdateGradeOrderingUseCase {

    override suspend operator fun invoke(value: GradeOrdering) {
        gradeRepository.updateGradeOrdering(value)
    }

}
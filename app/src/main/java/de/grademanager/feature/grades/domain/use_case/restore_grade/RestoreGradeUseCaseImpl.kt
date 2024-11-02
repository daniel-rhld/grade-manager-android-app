package de.grademanager.feature.grades.domain.use_case.restore_grade

import de.grademanager.core.data.model.DataResult
import de.grademanager.feature.grades.domain.repository.GradeRepository

class RestoreGradeUseCaseImpl(
    private val gradeRepository: GradeRepository
) : RestoreGradeUseCase {

    override suspend operator fun invoke(gradeId: Int): DataResult<Unit> {
        return gradeRepository.restoreById(id = gradeId)
    }

}
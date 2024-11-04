package de.grademanager.core.domain.use_case.grade_restore

import de.grademanager.common.util.DataResult
import de.grademanager.core.data.repository.grade.GradeRepository

class RestoreGradeUseCaseImpl(
    private val gradeRepository: GradeRepository
) : RestoreGradeUseCase {

    override suspend operator fun invoke(gradeId: Int): DataResult<Unit> {
        return gradeRepository.restoreById(id = gradeId)
    }

}
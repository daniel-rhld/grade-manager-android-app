package de.grademanager.core.domain.use_case.grade_delete

import de.grademanager.common.util.DataResult
import de.grademanager.core.data.repository.grade.GradeRepository

class DeleteGradeUseCaseImpl(
    private val gradeRepository: GradeRepository
) : DeleteGradeUseCase {

    override suspend operator fun invoke(gradeId: Int): DataResult<Unit> {
        return gradeRepository.deleteById(id = gradeId)
    }

}
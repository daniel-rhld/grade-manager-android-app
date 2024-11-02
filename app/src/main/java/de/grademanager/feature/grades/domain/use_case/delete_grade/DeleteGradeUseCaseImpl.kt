package de.grademanager.feature.grades.domain.use_case.delete_grade

import de.grademanager.core.data.model.DataResult
import de.grademanager.feature.grades.domain.repository.GradeRepository

class DeleteGradeUseCaseImpl(
    private val gradeRepository: GradeRepository
) : DeleteGradeUseCase {

    override suspend operator fun invoke(gradeId: Int): DataResult<Unit> {
        return gradeRepository.deleteById(id = gradeId)
    }

}
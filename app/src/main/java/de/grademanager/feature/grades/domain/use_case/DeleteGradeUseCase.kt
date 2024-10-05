package de.grademanager.feature.grades.domain.use_case

import de.grademanager.core.data.model.DataResult
import de.grademanager.feature.grades.data.repository.GradeRepository

class DeleteGradeUseCase(
    private val gradeRepository: GradeRepository
) {

    suspend operator fun invoke(gradeId: Int): DataResult<Unit> {
        return gradeRepository.deleteById(id = gradeId)
    }

}
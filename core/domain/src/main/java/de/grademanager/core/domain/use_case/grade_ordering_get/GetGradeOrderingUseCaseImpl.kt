package de.grademanager.core.domain.use_case.grade_ordering_get

import de.grademanager.core.data.repository.grade.GradeRepository
import de.grademanager.core.model.GradeOrdering
import kotlinx.coroutines.flow.Flow

class GetGradeOrderingUseCaseImpl(
    private val gradeRepository: GradeRepository
) : GetGradeOrderingUseCase {

    override fun invoke(): Flow<GradeOrdering> {
        return gradeRepository.getGradeOrderingSettings()
    }

}
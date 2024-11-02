package de.grademanager.feature.grades.domain.use_case.get_grade_ordering

import de.grademanager.feature.grades.domain.repository.GradeRepository
import de.grademanager.feature.subjects.domain.model.GradeOrdering
import kotlinx.coroutines.flow.Flow

class GetGradeOrderingUseCaseImpl(
    private val gradeRepository: GradeRepository
) : GetGradeOrderingUseCase {

    override fun invoke(): Flow<GradeOrdering> {
        return gradeRepository.getGradeOrderingSettings()
    }

}
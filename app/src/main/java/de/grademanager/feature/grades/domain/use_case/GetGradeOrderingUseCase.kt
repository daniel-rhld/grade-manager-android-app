package de.grademanager.feature.grades.domain.use_case

import de.grademanager.feature.grades.data.repository.GradeRepository
import de.grademanager.feature.subjects.domain.models.GradeOrdering
import kotlinx.coroutines.flow.Flow

class GetGradeOrderingUseCase(
    private val gradeRepository: GradeRepository
) {

    fun invoke(): Flow<GradeOrdering> {
        return gradeRepository.getGradeOrderingSettings()
    }

}
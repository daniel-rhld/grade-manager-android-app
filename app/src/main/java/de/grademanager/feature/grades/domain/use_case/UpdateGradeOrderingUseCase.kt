package de.grademanager.feature.grades.domain.use_case

import de.grademanager.feature.grades.data.repository.GradeRepository
import de.grademanager.feature.subjects.domain.models.GradeOrdering

class UpdateGradeOrderingUseCase(
    private val gradeRepository: GradeRepository
) {

    suspend operator fun invoke(value: GradeOrdering) {
        gradeRepository.updateGradeOrdering(value)
    }

}
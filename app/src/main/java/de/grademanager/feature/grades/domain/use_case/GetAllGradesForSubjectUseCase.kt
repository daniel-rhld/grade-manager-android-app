package de.grademanager.feature.grades.domain.use_case

import de.grademanager.feature.grades.data.repository.GradeRepository
import de.grademanager.feature.grades.domain.models.Grade
import kotlinx.coroutines.flow.Flow

class GetAllGradesForSubjectUseCase(
    private val gradeRepository: GradeRepository
) {

    suspend operator fun invoke(subjectId: Int): Flow<List<Grade>> {
        return gradeRepository.getAllGradesForSubject(subjectId = subjectId)
    }

}
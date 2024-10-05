package de.grademanager.feature.grades.domain.use_case

import de.grademanager.feature.grades.data.repository.GradeRepository
import de.grademanager.feature.grades.domain.models.Grade
import de.grademanager.feature.subjects.domain.models.GradeOrdering
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAllGradesForSubjectUseCase(
    private val gradeRepository: GradeRepository
) {

    operator fun invoke(subjectId: Int, gradeOrdering: GradeOrdering): Flow<List<Grade>> {
        return gradeRepository.getAllGradesForSubject(
            subjectId = subjectId
        ).map { list ->
            when (gradeOrdering) {
                is GradeOrdering.ReceivedAt -> {
                    if (gradeOrdering.ascending) {
                        list.sortedBy { it.receivedAt.time }
                    } else {
                        list.sortedByDescending { it.receivedAt.time }
                    }
                }

                is GradeOrdering.Value -> {
                    if (gradeOrdering.ascending) {
                        list.sortedBy { it.grade }
                    } else {
                        list.sortedByDescending { it.grade }
                    }
                }
            }
        }
    }

}
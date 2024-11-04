package de.grademanager.core.domain.use_case.grade_get_for_subject

import de.grademanager.core.data.repository.grade.GradeRepository
import de.grademanager.core.model.Grade
import de.grademanager.core.model.GradeOrdering
import de.grademanager.core.model.OrderingDirection
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetGradesForSubjectUseCaseImpl(
    private val gradeRepository: GradeRepository
) : GetGradesForSubjectUseCase {

    override operator fun invoke(subjectId: Int, gradeOrdering: GradeOrdering): Flow<List<Grade>> {
        return gradeRepository.getAllGradesForSubject(
            subjectId = subjectId
        ).map { list ->
            when (gradeOrdering) {
                is GradeOrdering.ReceivedAt -> when (gradeOrdering.direction) {
                    OrderingDirection.ASCENDING -> list.sortedBy { it.receivedAt.time }
                    OrderingDirection.DESCENDING -> list.sortedByDescending { it.receivedAt.time }
                }

                is GradeOrdering.Value -> {
                    when (gradeOrdering.direction) {
                        OrderingDirection.ASCENDING -> list.sortedBy { it.grade }
                        OrderingDirection.DESCENDING -> list.sortedByDescending { it.grade }
                    }
                }
            }
        }
    }

}
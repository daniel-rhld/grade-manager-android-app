package de.grademanager.feature.grades.domain.use_case.get_all_grades_for_subject

import de.grademanager.feature.grades.domain.repository.GradeRepository
import de.grademanager.feature.grades.domain.model.Grade
import de.grademanager.feature.subjects.domain.model.GradeOrdering
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAllGradesForSubjectUseCaseImpl(
    private val gradeRepository: GradeRepository
) : GetAllGradesForSubjectUseCase {

    override operator fun invoke(subjectId: Int, gradeOrdering: GradeOrdering): Flow<List<Grade>> {
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
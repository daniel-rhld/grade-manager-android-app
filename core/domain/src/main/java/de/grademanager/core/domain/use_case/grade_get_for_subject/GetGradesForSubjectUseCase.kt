package de.grademanager.core.domain.use_case.grade_get_for_subject

import de.grademanager.core.model.Grade
import de.grademanager.core.model.GradeOrdering
import kotlinx.coroutines.flow.Flow

interface GetGradesForSubjectUseCase {

    operator fun invoke(subjectId: Int, gradeOrdering: GradeOrdering): Flow<List<Grade>>

}
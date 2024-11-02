package de.grademanager.feature.grades.domain.use_case.get_all_grades_for_subject

import de.grademanager.feature.grades.domain.model.Grade
import de.grademanager.feature.subjects.domain.model.GradeOrdering
import kotlinx.coroutines.flow.Flow

interface GetAllGradesForSubjectUseCase {

    operator fun invoke(subjectId: Int, gradeOrdering: GradeOrdering): Flow<List<Grade>>

}
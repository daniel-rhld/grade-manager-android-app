package de.grademanager.feature.grades.domain.use_case.get_grade_ordering

import de.grademanager.feature.subjects.domain.model.GradeOrdering
import kotlinx.coroutines.flow.Flow

interface GetGradeOrderingUseCase {

    fun invoke(): Flow<GradeOrdering>

}
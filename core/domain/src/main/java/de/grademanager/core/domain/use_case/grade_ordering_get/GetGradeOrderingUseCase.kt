package de.grademanager.core.domain.use_case.grade_ordering_get

import de.grademanager.core.model.GradeOrdering
import kotlinx.coroutines.flow.Flow

interface GetGradeOrderingUseCase {

    fun invoke(): Flow<GradeOrdering>

}
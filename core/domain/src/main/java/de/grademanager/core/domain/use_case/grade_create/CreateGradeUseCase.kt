package de.grademanager.core.domain.use_case.grade_create

import de.grademanager.common.util.DataResult
import java.util.Date

interface CreateGradeUseCase {

    suspend operator fun invoke(
        grade: String,
        weighting: Double,
        description: String,
        receivedAt: Date,
        subjectId: Int
    ): DataResult<Unit>

}
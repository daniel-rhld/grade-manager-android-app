package de.grademanager.feature.grades.domain.use_case.create_grade

import de.grademanager.core.data.model.DataResult
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
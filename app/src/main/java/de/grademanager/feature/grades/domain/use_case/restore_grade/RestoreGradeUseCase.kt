package de.grademanager.feature.grades.domain.use_case.restore_grade

import de.grademanager.core.data.model.DataResult

interface RestoreGradeUseCase {

    suspend operator fun invoke(gradeId: Int): DataResult<Unit>

}
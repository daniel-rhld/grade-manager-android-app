package de.grademanager.core.domain.use_case.grade_restore

import de.grademanager.common.util.DataResult

interface RestoreGradeUseCase {

    suspend operator fun invoke(gradeId: Int): DataResult<Unit>

}
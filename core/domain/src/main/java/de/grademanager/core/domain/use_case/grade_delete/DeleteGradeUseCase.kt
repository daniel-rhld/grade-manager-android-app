package de.grademanager.core.domain.use_case.grade_delete

import de.grademanager.common.util.DataResult

interface DeleteGradeUseCase {

    suspend operator fun invoke(gradeId: Int): DataResult<Unit>

}
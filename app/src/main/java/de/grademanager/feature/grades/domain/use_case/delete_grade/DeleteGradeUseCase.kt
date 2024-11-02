package de.grademanager.feature.grades.domain.use_case.delete_grade

import de.grademanager.core.data.model.DataResult

interface DeleteGradeUseCase {

    suspend operator fun invoke(gradeId: Int): DataResult<Unit>

}
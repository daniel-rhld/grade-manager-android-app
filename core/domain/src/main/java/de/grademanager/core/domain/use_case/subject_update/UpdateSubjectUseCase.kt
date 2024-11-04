package de.grademanager.core.domain.use_case.subject_update

import de.grademanager.common.util.DataResult

interface UpdateSubjectUseCase {

    suspend operator fun invoke(id: Int, name: String): DataResult<Unit>

}
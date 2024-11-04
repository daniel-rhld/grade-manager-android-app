package de.grademanager.core.domain.use_case.subject_create

import de.grademanager.common.util.DataResult

interface CreateSubjectUseCase {

    suspend operator fun invoke(name: String): DataResult<Unit>

}
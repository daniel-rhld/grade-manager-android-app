package de.grademanager.feature.subjects.domain.use_case.update_subject

import de.grademanager.core.data.model.DataResult

interface UpdateSubjectUseCase {

    suspend operator fun invoke(id: Int, name: String): DataResult<Unit>

}
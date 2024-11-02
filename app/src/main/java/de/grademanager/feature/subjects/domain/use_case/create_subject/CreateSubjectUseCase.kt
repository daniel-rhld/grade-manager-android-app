package de.grademanager.feature.subjects.domain.use_case.create_subject

import de.grademanager.core.data.model.DataResult

interface CreateSubjectUseCase {

    suspend operator fun invoke(name: String): DataResult<Unit>

}
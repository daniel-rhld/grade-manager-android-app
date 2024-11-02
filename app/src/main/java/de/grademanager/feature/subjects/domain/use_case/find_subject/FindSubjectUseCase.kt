package de.grademanager.feature.subjects.domain.use_case.find_subject

import de.grademanager.core.data.model.DataResult
import de.grademanager.feature.subjects.domain.model.Subject

interface FindSubjectUseCase {

    suspend operator fun invoke(subjectId: Int): DataResult<Subject>

}
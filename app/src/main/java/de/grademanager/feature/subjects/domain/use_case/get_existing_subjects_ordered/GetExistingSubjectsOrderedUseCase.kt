package de.grademanager.feature.subjects.domain.use_case.get_existing_subjects_ordered

import de.grademanager.feature.subjects.domain.model.Subject
import kotlinx.coroutines.flow.Flow

interface GetExistingSubjectsOrderedUseCase {

    operator fun invoke(orderColumn: String, orderAscending: Boolean): Flow<List<Subject>>

}
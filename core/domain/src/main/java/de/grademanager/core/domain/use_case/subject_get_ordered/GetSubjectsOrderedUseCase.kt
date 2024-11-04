package de.grademanager.core.domain.use_case.subject_get_ordered

import de.grademanager.core.model.Subject
import kotlinx.coroutines.flow.Flow

interface GetSubjectsOrderedUseCase {

    operator fun invoke(orderColumn: String, orderAscending: Boolean): Flow<List<Subject>>

}
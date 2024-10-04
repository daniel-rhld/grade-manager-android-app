package de.grademanager.feature.subjects.domain.use_cases

import de.grademanager.feature.subjects.data.repository.SubjectRepository
import de.grademanager.feature.subjects.domain.models.Subject
import kotlinx.coroutines.flow.Flow

class GetExistingSubjectsOrdered(
    private val subjectRepository: SubjectRepository
) {

    operator fun invoke(
        orderColumn: String,
        orderAscending: Boolean
    ): Flow<List<Subject>> {
        return subjectRepository.getAllExistingSubjectsOrdered(
            orderColumn = orderColumn,
            orderAscending = orderAscending
        )
    }

}
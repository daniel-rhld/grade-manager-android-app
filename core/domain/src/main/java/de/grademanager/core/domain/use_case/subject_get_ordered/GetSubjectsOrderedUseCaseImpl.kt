package de.grademanager.core.domain.use_case.subject_get_ordered

import de.grademanager.core.data.repository.subject.SubjectRepository
import de.grademanager.core.model.Subject
import kotlinx.coroutines.flow.Flow

class GetSubjectsOrderedUseCaseImpl(
    private val subjectRepository: SubjectRepository
) : GetSubjectsOrderedUseCase {

    override operator fun invoke(
        orderColumn: String,
        orderAscending: Boolean
    ): Flow<List<Subject>> {
        return subjectRepository.getAllExistingSubjectsOrdered(
            orderColumn = orderColumn,
            orderAscending = orderAscending
        )
    }

}
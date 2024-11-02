package de.grademanager.feature.subjects.domain.use_case.get_existing_subjects_ordered

import de.grademanager.feature.subjects.domain.repository.SubjectRepository
import de.grademanager.feature.subjects.domain.model.Subject
import kotlinx.coroutines.flow.Flow

class GetExistingSubjectsOrderedUseCaseImpl(
    private val subjectRepository: SubjectRepository
) : GetExistingSubjectsOrderedUseCase {

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
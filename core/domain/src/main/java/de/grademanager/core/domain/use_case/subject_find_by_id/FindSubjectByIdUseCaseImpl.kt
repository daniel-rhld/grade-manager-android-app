package de.grademanager.core.domain.use_case.subject_find_by_id

import de.grademanager.common.util.DataResult
import de.grademanager.core.data.repository.subject.SubjectRepository
import de.grademanager.core.model.Subject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FindSubjectByIdUseCaseImpl(
    private val subjectRepository: SubjectRepository
) : FindSubjectByIdUseCase {


    override suspend fun findSubjectById(id: Int): Subject? {
        val result = subjectRepository.findById(id)
        return (result as? DataResult.Success)?.value
    }

    override fun findSubjectByIdAsFlow(id: Int): Flow<Subject?> {
        return subjectRepository.findByIdAsFlow(id = id).map { result ->
            (result as? DataResult.Success)?.value
        }
    }
}
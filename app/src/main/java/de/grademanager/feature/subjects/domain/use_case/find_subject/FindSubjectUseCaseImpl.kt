package de.grademanager.feature.subjects.domain.use_case.find_subject

import de.grademanager.core.data.model.DataResult
import de.grademanager.feature.subjects.domain.repository.SubjectRepository
import de.grademanager.feature.subjects.domain.model.Subject

class FindSubjectUseCaseImpl(
    private val subjectRepository: SubjectRepository
) : FindSubjectUseCase {

    override suspend operator fun invoke(subjectId: Int): DataResult<Subject> {
        return subjectRepository.findById(id = subjectId)
    }

}
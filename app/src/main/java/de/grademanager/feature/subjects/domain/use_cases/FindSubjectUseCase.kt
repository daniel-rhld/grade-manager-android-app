package de.grademanager.feature.subjects.domain.use_cases

import de.grademanager.core.data.model.DataResult
import de.grademanager.feature.subjects.data.repository.SubjectRepository
import de.grademanager.feature.subjects.domain.models.Subject

class FindSubjectUseCase(
    private val subjectRepository: SubjectRepository
) {

    suspend operator fun invoke(subjectId: Int): DataResult<Subject> {
        return subjectRepository.findById(id = subjectId)
    }

}
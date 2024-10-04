package de.grademanager.feature.subjects.domain.use_cases

import de.grademanager.R
import de.grademanager.core.data.model.DataResult
import de.grademanager.core.data.model.StringWrapper
import de.grademanager.feature.subjects.data.model.entity.SubjectEntity
import de.grademanager.feature.subjects.data.repository.SubjectRepository
import java.util.Date

class CreateSubjectUseCase(
    private val subjectRepository: SubjectRepository
) {

    suspend operator fun invoke(name: String): DataResult<Unit> {
        if (name.isNotBlank()) {
            return subjectRepository.upsert(
                value = SubjectEntity(
                    name = name,
                    createdAt = Date()
                )
            )
        } else {
            return DataResult.Failure(
                error = StringWrapper.Resource(R.string.manage_subject_dialog_error_no_name)
            )
        }
    }

}
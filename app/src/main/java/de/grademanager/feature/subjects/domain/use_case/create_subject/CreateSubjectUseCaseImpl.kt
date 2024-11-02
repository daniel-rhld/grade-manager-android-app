package de.grademanager.feature.subjects.domain.use_case.create_subject

import de.grademanager.R
import de.grademanager.core.data.model.DataResult
import de.grademanager.core.data.model.StringWrapper
import de.grademanager.core.data.model.asStringWrapper
import de.grademanager.feature.subjects.data.model.entity.SubjectEntity
import de.grademanager.feature.subjects.domain.repository.SubjectRepository
import java.util.Date

class CreateSubjectUseCaseImpl(
    private val subjectRepository: SubjectRepository
) : CreateSubjectUseCase {

    override suspend operator fun invoke(name: String): DataResult<Unit> {
        if (subjectRepository.doesSubjectAlreadyExist(name)) {
            return DataResult.Failure(
                error = R.string.manage_subject_dialog_error_subject_with_same_name_already_exists.asStringWrapper()
            )
        }

        return if (name.isNotBlank()) {
            subjectRepository.upsert(
                value = SubjectEntity(
                    name = name,
                    createdAt = Date()
                )
            )
        } else {
            DataResult.Failure(
                error = StringWrapper.Resource(R.string.manage_subject_dialog_error_no_name)
            )
        }
    }

}
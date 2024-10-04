package de.grademanager.feature.subjects.domain.use_cases

import de.grademanager.R
import de.grademanager.core.data.model.DataResult
import de.grademanager.core.data.model.asStringWrapper
import de.grademanager.feature.subjects.data.repository.SubjectRepository
import de.grademanager.feature.subjects.domain.models.mapToEntity

class UpdateSubjectUseCase(
    private val subjectRepository: SubjectRepository
) {

    suspend operator fun invoke(id: Int, name: String): DataResult<Unit> {
        if (subjectRepository.doesSubjectAlreadyExist(name)) {
            return DataResult.Failure(
                R.string.manage_subject_dialog_error_subject_with_same_name_already_exists.asStringWrapper()
            )
        }

        subjectRepository.findById(id = id).let { result ->
            when (result) {
                is DataResult.Success -> {
                    return subjectRepository.upsert(
                        value = result.value.mapToEntity().copy(
                            name = name
                        )
                    )
                }

                is DataResult.Failure -> {
                    return DataResult.Failure(error = result.error)
                }
            }
        }
    }

}
package de.grademanager.feature.subjects.domain.use_case.update_subject

import de.grademanager.R
import de.grademanager.core.data.model.DataResult
import de.grademanager.core.data.model.asStringWrapper
import de.grademanager.feature.subjects.domain.repository.SubjectRepository
import de.grademanager.feature.subjects.domain.model.mapToEntity
import de.grademanager.feature.subjects.domain.use_case.find_subject.FindSubjectUseCase
import de.grademanager.feature.subjects.domain.use_case.find_subject.FindSubjectUseCaseImpl

class UpdateSubjectUseCaseImpl(
    private val subjectRepository: SubjectRepository,
    private val findSubjectUseCase: FindSubjectUseCase
) : UpdateSubjectUseCase {

    override suspend operator fun invoke(id: Int, name: String): DataResult<Unit> {
        if (subjectRepository.doesSubjectAlreadyExist(name)) {
            return DataResult.Failure(
                R.string.manage_subject_dialog_error_subject_with_same_name_already_exists.asStringWrapper()
            )
        }

        findSubjectUseCase.invoke(subjectId = id).let { result ->
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
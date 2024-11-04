package de.grademanager.core.domain.use_case.subject_update

import de.grademanager.common.util.DataResult
import de.grademanager.common.util.asStringWrapper
import de.grademanager.core.data.repository.subject.SubjectRepository
import de.grademanager.core.database.model.subject.SubjectEntity

class UpdateSubjectUseCaseImpl(
    private val subjectRepository: SubjectRepository
) : UpdateSubjectUseCase {

    override suspend operator fun invoke(id: Int, name: String): DataResult<Unit> {
        if (subjectRepository.doesSubjectAlreadyExist(name)) {
            return DataResult.Failure(
                // TODO: Change error message
                "Subject with same name already exists".asStringWrapper()
            )
        }

        subjectRepository.findById(id = id).let { result ->
            when (result) {
                is DataResult.Success -> {
                    return subjectRepository.upsert(
                        // TODO: Change this, domain class shouldn't directly access data-layer class
                        value = SubjectEntity(
                            id = id,
                            name = name,
                            createdAt = result.value.createdAt,
                            updatedAt = result.value.updatedAt,
                            deletedAt = result.value.deletedAt
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
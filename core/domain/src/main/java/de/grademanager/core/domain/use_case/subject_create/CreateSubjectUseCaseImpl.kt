package de.grademanager.core.domain.use_case.subject_create

import de.grademanager.common.util.DataResult
import de.grademanager.common.util.asStringWrapper
import de.grademanager.core.data.repository.subject.SubjectRepository
import de.grademanager.core.database.model.subject.SubjectEntity
import java.util.Date

class CreateSubjectUseCaseImpl(
    private val subjectRepository: SubjectRepository
) : CreateSubjectUseCase {

    override suspend operator fun invoke(name: String): DataResult<Unit> {
        if (subjectRepository.doesSubjectAlreadyExist(name)) {
            return DataResult.Failure(
                // TODO: Change error message
                error = "Subject already exists".asStringWrapper()
            )
        }
        return if (name.isNotBlank()) {
            subjectRepository.upsert(
                // TODO: Change this, domain class shouldn't directly access data-layer class
                value = SubjectEntity(
                    name = name,
                    createdAt = Date()
                )
            )
        } else {
            DataResult.Failure(
                // TODO: Change error message
                error = "No Subject name".asStringWrapper()
            )
        }
    }

}
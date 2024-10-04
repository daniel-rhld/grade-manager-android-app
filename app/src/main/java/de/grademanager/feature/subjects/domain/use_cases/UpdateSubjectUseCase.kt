package de.grademanager.feature.subjects.domain.use_cases

import de.grademanager.core.data.model.DataResult
import de.grademanager.feature.subjects.data.repository.SubjectRepository
import de.grademanager.feature.subjects.domain.models.mapToEntity

class UpdateSubjectUseCase(
    private val subjectRepository: SubjectRepository
) {

    suspend operator fun invoke(id: Int, name: String): DataResult<Unit> {
        // TODO: Before update, check if there already exists a subject with this name
        subjectRepository.findById(id = id).let { result ->
            if (result is DataResult.Success) {
                return subjectRepository.upsert(
                    value = result.value.mapToEntity().copy(
                        name = name
                    )
                )
            } else {
                return DataResult.Failure(error = null)
            }
        }
    }

}
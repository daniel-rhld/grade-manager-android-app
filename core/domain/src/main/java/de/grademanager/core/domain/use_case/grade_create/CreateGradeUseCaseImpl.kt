package de.grademanager.core.domain.use_case.grade_create

import de.grademanager.common.util.DataResult
import de.grademanager.common.util.asStringWrapper
import de.grademanager.core.data.repository.grade.GradeRepository
import de.grademanager.core.data.repository.subject.SubjectRepository
import de.grademanager.core.database.model.grade.GradeEntity
import java.util.Date

class CreateGradeUseCaseImpl(
    private val gradeRepository: GradeRepository,
    private val subjectRepository: SubjectRepository
) : CreateGradeUseCase {

    override suspend operator fun invoke(
        grade: String,
        weighting: Double,
        description: String,
        receivedAt: Date,
        subjectId: Int
    ): DataResult<Unit> {
        val gradeValue = try {
            grade.replace(",", ".").toDouble()
        } catch (e: NumberFormatException) {
            null
        }

        if (gradeValue == null || gradeValue < 1.0 || gradeValue > 6.0) {
            return DataResult.Failure(
                // TODO: Change error message
                error = "Invalid grade".asStringWrapper()
            )
        }

        if (weighting < 0.25 || weighting > 2.0) {
            return DataResult.Failure(
                // TODO: Change error message
                error = "Invalid weighting".asStringWrapper()
            )
        }

        if (receivedAt.after(Date())) {
            return DataResult.Failure(
                // TODO: Change error message
                error = "Invalid received at".asStringWrapper()
            )
        }

        subjectRepository.findById(subjectId).let { result ->
            when (result) {
                is DataResult.Success -> {
                    return gradeRepository.upsert(
                        // TODO: Change this, domain class shouldn't directly access data-layer class
                        entity = GradeEntity(
                            grade = gradeValue,
                            weighting = weighting,
                            description = description.ifBlank { null },
                            subjectId = result.value.id,
                            receivedAt = receivedAt,
                            createdAt = Date()
                        )
                    )
                }

                is DataResult.Failure -> {
                    return DataResult.Failure(
                        // TODO: Change error message
                        error = "Subject not found".asStringWrapper()
                    )
                }
            }
        }
    }

}
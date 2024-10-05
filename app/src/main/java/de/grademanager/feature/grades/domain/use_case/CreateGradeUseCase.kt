package de.grademanager.feature.grades.domain.use_case

import de.grademanager.core.data.model.DataResult
import de.grademanager.core.data.model.asStringWrapper
import de.grademanager.feature.grades.data.model.entity.GradeEntity
import de.grademanager.feature.grades.data.repository.GradeRepository
import de.grademanager.feature.subjects.domain.use_cases.FindSubjectUseCase
import java.util.Date

class CreateGradeUseCase(
    private val gradeRepository: GradeRepository,
    private val findSubjectUseCase: FindSubjectUseCase
) {

    suspend operator fun invoke(
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
            return DataResult.Failure(error = "invalid grade error msg".asStringWrapper())
        }

        if (weighting < 0.25 || weighting > 2.0) {
            return DataResult.Failure(error = "invalid weighting error msg".asStringWrapper())
        }

        if (receivedAt.after(Date())) {
            return DataResult.Failure(error = "invalid received at error msg".asStringWrapper())
        }

        findSubjectUseCase.invoke(
            subjectId = subjectId
        ).let { result ->
            when (result) {
                is DataResult.Success -> {
                    return gradeRepository.upsert(
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
                    return DataResult.Failure("subject not found error".asStringWrapper())
                }
            }
        }
    }

}
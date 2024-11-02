package de.grademanager.feature.grades.domain.use_case.create_grade

import de.grademanager.R
import de.grademanager.core.data.model.DataResult
import de.grademanager.core.data.model.asStringWrapper
import de.grademanager.feature.grades.data.model.entity.GradeEntity
import de.grademanager.feature.grades.domain.repository.GradeRepository
import de.grademanager.feature.subjects.domain.use_case.create_subject.CreateSubjectUseCase
import de.grademanager.feature.subjects.domain.use_case.find_subject.FindSubjectUseCase
import de.grademanager.feature.subjects.domain.use_case.find_subject.FindSubjectUseCaseImpl
import java.util.Date

class CreateGradeUseCaseImpl(
    private val gradeRepository: GradeRepository,
    private val findSubjectUseCase: FindSubjectUseCase
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
                error = R.string.add_grade_error_invalid_grade.asStringWrapper()
            )
        }

        if (weighting < 0.25 || weighting > 2.0) {
            return DataResult.Failure(
                error = R.string.add_grade_error_invalid_weighting.asStringWrapper()
            )
        }

        if (receivedAt.after(Date())) {
            return DataResult.Failure(
                error = R.string.add_grade_error_invalid_received_at.asStringWrapper()
            )
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
                    return DataResult.Failure(
                        error = R.string.add_grade_error_subject_not_found.asStringWrapper()
                    )
                }
            }
        }
    }

}
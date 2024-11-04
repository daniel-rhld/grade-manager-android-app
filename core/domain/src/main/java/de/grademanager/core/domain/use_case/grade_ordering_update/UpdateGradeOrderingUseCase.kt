package de.grademanager.core.domain.use_case.grade_ordering_update

import de.grademanager.core.model.GradeOrdering

interface UpdateGradeOrderingUseCase {

    suspend operator fun invoke(value: GradeOrdering)

}
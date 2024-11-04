package de.grademanager.core.domain.di

import de.grademanager.core.domain.use_case.calculate_average_grade.CalculateAverageGradeUseCase
import de.grademanager.core.domain.use_case.calculate_average_grade.CalculateAverageGradeUseCaseImpl
import de.grademanager.core.domain.use_case.grade_create.CreateGradeUseCase
import de.grademanager.core.domain.use_case.grade_create.CreateGradeUseCaseImpl
import de.grademanager.core.domain.use_case.grade_delete.DeleteGradeUseCase
import de.grademanager.core.domain.use_case.grade_delete.DeleteGradeUseCaseImpl
import de.grademanager.core.domain.use_case.grade_get_for_subject.GetGradesForSubjectUseCase
import de.grademanager.core.domain.use_case.grade_get_for_subject.GetGradesForSubjectUseCaseImpl
import de.grademanager.core.domain.use_case.grade_ordering_get.GetGradeOrderingUseCase
import de.grademanager.core.domain.use_case.grade_ordering_get.GetGradeOrderingUseCaseImpl
import de.grademanager.core.domain.use_case.grade_ordering_update.UpdateGradeOrderingUseCase
import de.grademanager.core.domain.use_case.grade_ordering_update.UpdateGradeOrderingUseCaseImpl
import de.grademanager.core.domain.use_case.grade_restore.RestoreGradeUseCase
import de.grademanager.core.domain.use_case.grade_restore.RestoreGradeUseCaseImpl
import de.grademanager.core.domain.use_case.subject_create.CreateSubjectUseCase
import de.grademanager.core.domain.use_case.subject_create.CreateSubjectUseCaseImpl
import de.grademanager.core.domain.use_case.subject_get_ordered.GetSubjectsOrderedUseCase
import de.grademanager.core.domain.use_case.subject_get_ordered.GetSubjectsOrderedUseCaseImpl
import de.grademanager.core.domain.use_case.subject_update.UpdateSubjectUseCase
import de.grademanager.core.domain.use_case.subject_update.UpdateSubjectUseCaseImpl
import org.koin.dsl.module

val UseCaseModule = module {
    single<CalculateAverageGradeUseCase> {
        CalculateAverageGradeUseCaseImpl()
    }

    single<CreateGradeUseCase> {
        CreateGradeUseCaseImpl(
            gradeRepository = get(),
            subjectRepository = get()
        )
    }

    single<DeleteGradeUseCase> {
        DeleteGradeUseCaseImpl(
            gradeRepository = get()
        )
    }

    single<GetGradesForSubjectUseCase> {
        GetGradesForSubjectUseCaseImpl(
            gradeRepository = get()
        )
    }

    single<GetGradeOrderingUseCase> {
        GetGradeOrderingUseCaseImpl(
            gradeRepository = get()
        )
    }

    single<UpdateGradeOrderingUseCase> {
        UpdateGradeOrderingUseCaseImpl(
            gradeRepository = get()
        )
    }

    single<RestoreGradeUseCase> {
        RestoreGradeUseCaseImpl(
            gradeRepository = get()
        )
    }

    single<CreateSubjectUseCase> {
        CreateSubjectUseCaseImpl(
            subjectRepository = get()
        )
    }

    single<GetSubjectsOrderedUseCase> {
        GetSubjectsOrderedUseCaseImpl(
            subjectRepository = get()
        )
    }

    single<UpdateSubjectUseCase> {
        UpdateSubjectUseCaseImpl(
            subjectRepository = get()
        )
    }

}
package de.grademanager.feature.subjects.di

import de.grademanager.feature.subjects.domain.repository.SubjectRepository
import de.grademanager.feature.subjects.data.repository.SubjectRepositoryImpl
import de.grademanager.feature.subjects.domain.use_case.create_subject.CreateSubjectUseCaseImpl
import de.grademanager.feature.subjects.domain.use_case.find_subject.FindSubjectUseCaseImpl
import de.grademanager.feature.subjects.domain.use_case.get_existing_subjects_ordered.GetExistingSubjectsOrderedUseCaseImpl
import de.grademanager.feature.subjects.domain.use_case.update_subject.UpdateSubjectUseCaseImpl
import de.grademanager.feature.subjects.domain.use_case.calculate_average_subject_grade.CalculateAverageSubjectGradeUseCase
import de.grademanager.feature.subjects.domain.use_case.calculate_average_subject_grade.CalculateAverageSubjectGradeUseCaseImpl
import de.grademanager.feature.subjects.domain.use_case.create_subject.CreateSubjectUseCase
import de.grademanager.feature.subjects.domain.use_case.find_subject.FindSubjectUseCase
import de.grademanager.feature.subjects.domain.use_case.get_existing_subjects_ordered.GetExistingSubjectsOrderedUseCase
import de.grademanager.feature.subjects.domain.use_case.update_subject.UpdateSubjectUseCase
import de.grademanager.feature.subjects.presentation.detail.SubjectDetailViewModel
import de.grademanager.feature.subjects.presentation.overview.SubjectsOverviewViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val SubjectsModule = module {
    single<SubjectRepository> {
        SubjectRepositoryImpl(
            database = get()
        )
    }

    single<GetExistingSubjectsOrderedUseCase> {
        GetExistingSubjectsOrderedUseCaseImpl(
            subjectRepository = get()
        )
    }

    single<CalculateAverageSubjectGradeUseCase> {
        CalculateAverageSubjectGradeUseCaseImpl()
    }

    single<FindSubjectUseCase> {
        FindSubjectUseCaseImpl(
            subjectRepository = get()
        )
    }

    single<CreateSubjectUseCase> {
        CreateSubjectUseCaseImpl(
            subjectRepository = get()
        )
    }

    single<UpdateSubjectUseCase> {
        UpdateSubjectUseCaseImpl(
            subjectRepository = get(),
            findSubjectUseCase = get()
        )
    }

    viewModel {
        SubjectsOverviewViewModel(
            getExistingSubjectsOrdered = get(),
            calculateAverageSubjectGradeUseCase = get(),
            createSubjectUseCase = get(),
            updateSubjectUseCase = get()
        )
    }

    viewModel {
        SubjectDetailViewModel(
            getAllGradesForSubjectUseCase = get(),
            createGradeUseCase = get(),
            deleteGradeUseCase = get(),
            restoreGradeUseCase = get(),
            getGradeOrderingUseCase = get(),
            updateGradeOrderingUseCase = get(),
            calculateAverageGradeUseCase = get(),
            snackbarController = get(),
            savedStateHandle = get()
        )
    }
}
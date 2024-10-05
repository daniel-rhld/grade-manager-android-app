package de.grademanager.feature.subjects.di

import de.grademanager.feature.subjects.data.repository.SubjectRepository
import de.grademanager.feature.subjects.data.repository.SubjectRepositoryImpl
import de.grademanager.feature.subjects.domain.use_cases.CreateSubjectUseCase
import de.grademanager.feature.subjects.domain.use_cases.FindSubjectUseCase
import de.grademanager.feature.subjects.domain.use_cases.GetExistingSubjectsOrdered
import de.grademanager.feature.subjects.domain.use_cases.UpdateSubjectUseCase
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

    single {
        GetExistingSubjectsOrdered(
            subjectRepository = get()
        )
    }

    single {
        FindSubjectUseCase(
            subjectRepository = get()
        )
    }

    single {
        CreateSubjectUseCase(
            subjectRepository = get()
        )
    }

    single {
        UpdateSubjectUseCase(
            subjectRepository = get(),
            findSubjectUseCase = get()
        )
    }

    viewModel {
        SubjectsOverviewViewModel(
            getExistingSubjectsOrdered = get(),
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
            snackbarController = get(),
            savedStateHandle = get()
        )
    }
}
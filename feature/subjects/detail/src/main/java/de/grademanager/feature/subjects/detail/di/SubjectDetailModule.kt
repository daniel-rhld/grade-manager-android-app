package de.grademanager.feature.subjects.detail.di

import de.grademanager.feature.subjects.detail.SubjectDetailViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val SubjectDetailModule = module {

    viewModel {
        SubjectDetailViewModel(
            findSubjectByIdUseCase = get(),

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
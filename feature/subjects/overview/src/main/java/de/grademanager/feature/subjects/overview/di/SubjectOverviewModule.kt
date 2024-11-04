package de.grademanager.feature.subjects.overview.di

import de.grademanager.feature.subjects.overview.SubjectsOverviewViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val SubjectOverviewModule = module {
    viewModel {
        SubjectsOverviewViewModel(
            getSubjectsOrderedUseCase = get(),
            createSubjectUseCase = get(),
            updateSubjectUseCase = get(),
            calculateAverageSubjectGradeUseCase = get()
        )
    }
}
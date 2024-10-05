package de.grademanager.feature.grades.di

import de.grademanager.feature.grades.data.repository.GradeRepository
import de.grademanager.feature.grades.data.repository.GradeRepositoryImpl
import de.grademanager.feature.grades.domain.use_case.CreateGradeUseCase
import de.grademanager.feature.grades.domain.use_case.GetAllGradesForSubjectUseCase
import org.koin.dsl.module

val GradeModule = module {
    single<GradeRepository> {
        GradeRepositoryImpl(
            database = get()
        )
    }

    single {
        GetAllGradesForSubjectUseCase(
            gradeRepository = get()
        )
    }

    single {
        CreateGradeUseCase(
            gradeRepository = get(),
            findSubjectUseCase = get()
        )
    }
}
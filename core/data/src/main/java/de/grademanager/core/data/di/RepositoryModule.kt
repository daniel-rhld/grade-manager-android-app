package de.grademanager.core.data.di

import de.grademanager.core.data.repository.grade.GradeRepository
import de.grademanager.core.data.repository.grade.GradeRepositoryImpl
import de.grademanager.core.data.repository.subject.SubjectRepository
import de.grademanager.core.data.repository.subject.SubjectRepositoryImpl
import org.koin.dsl.module

val RepositoryModule = module {

    single<GradeRepository> {
        GradeRepositoryImpl(
            gradeDao = get(),
            gradeOrderingSettingsDataStore = get()
        )
    }

    single<SubjectRepository> {
        SubjectRepositoryImpl(
            subjectDao = get()
        )
    }

}
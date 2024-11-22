package de.grademanager.core.data.di

import de.grademanager.core.data.repository.auth.AuthRepository
import de.grademanager.core.data.repository.auth.AuthRepositoryImpl
import de.grademanager.core.data.repository.grade.GradeRepository
import de.grademanager.core.data.repository.grade.GradeRepositoryImpl
import de.grademanager.core.data.repository.subject.SubjectRepository
import de.grademanager.core.data.repository.subject.SubjectRepositoryImpl
import de.grademanger.core.datastore.di.DATASTORE_QUALIFIER_USER
import org.koin.core.qualifier.StringQualifier
import org.koin.dsl.module

val RepositoryModule = module {

    single<AuthRepository> {
        AuthRepositoryImpl(
            httpClient = get(),
            sessionManager = get(),
            userDataStore = get(
                qualifier = StringQualifier(DATASTORE_QUALIFIER_USER)
            )
        )
    }

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
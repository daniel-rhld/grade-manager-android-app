package de.grademanager.feature.grades.di

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.dataStoreFile
import de.grademanager.feature.grades.data.model.local_settings.GradeOrderingSettingsLocal
import de.grademanager.feature.grades.data.repository.GradeRepository
import de.grademanager.feature.grades.data.repository.GradeRepositoryImpl
import de.grademanager.feature.grades.domain.use_case.CreateGradeUseCase
import de.grademanager.feature.grades.domain.use_case.DeleteGradeUseCase
import de.grademanager.feature.grades.domain.use_case.GetAllGradesForSubjectUseCase
import de.grademanager.feature.grades.domain.use_case.GetGradeOrderingUseCase
import de.grademanager.feature.grades.domain.use_case.RestoreGradeUseCase
import de.grademanager.feature.grades.domain.use_case.UpdateGradeOrderingUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val GradeModule = module {
    single<DataStore<GradeOrderingSettingsLocal>> {
        DataStoreFactory.create(
            serializer = GradeOrderingSettingsLocal.DataStoreSerializer,
            corruptionHandler = ReplaceFileCorruptionHandler {
                GradeOrderingSettingsLocal.DataStoreSerializer.defaultValue
            },
            migrations = listOf(),
            scope = CoroutineScope(Dispatchers.IO),
            produceFile = { androidContext().dataStoreFile("grade-ordering-settings.json") }
        )
    }

    single<GradeRepository> {
        GradeRepositoryImpl(
            database = get(),
            gradeOrderingSettingsDataStore = get()
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

    single {
        DeleteGradeUseCase(
            gradeRepository = get()
        )
    }

    single {
        RestoreGradeUseCase(
            gradeRepository = get()
        )
    }

    single {
        GetGradeOrderingUseCase(
            gradeRepository = get()
        )
    }

    single {
        UpdateGradeOrderingUseCase(
            gradeRepository = get()
        )
    }
}
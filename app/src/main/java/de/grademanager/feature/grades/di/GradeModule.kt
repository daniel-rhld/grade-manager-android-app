package de.grademanager.feature.grades.di

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.dataStoreFile
import de.grademanager.feature.grades.data.model.local_settings.GradeOrderingSettingsLocal
import de.grademanager.feature.grades.domain.repository.GradeRepository
import de.grademanager.feature.grades.data.repository.GradeRepositoryImpl
import de.grademanager.feature.grades.domain.use_case.create_grade.CreateGradeUseCaseImpl
import de.grademanager.feature.grades.domain.use_case.delete_grade.DeleteGradeUseCaseImpl
import de.grademanager.feature.grades.domain.use_case.get_all_grades_for_subject.GetAllGradesForSubjectUseCaseImpl
import de.grademanager.feature.grades.domain.use_case.get_grade_ordering.GetGradeOrderingUseCaseImpl
import de.grademanager.feature.grades.domain.use_case.restore_grade.RestoreGradeUseCaseImpl
import de.grademanager.feature.grades.domain.use_case.update_grade_ordering.UpdateGradeOrderingUseCaseImpl
import de.grademanager.feature.grades.domain.use_case.calculate_average_grade.CalculateAverageGradeUseCase
import de.grademanager.feature.grades.domain.use_case.calculate_average_grade.CalculateAverageGradeUseCaseImpl
import de.grademanager.feature.grades.domain.use_case.create_grade.CreateGradeUseCase
import de.grademanager.feature.grades.domain.use_case.delete_grade.DeleteGradeUseCase
import de.grademanager.feature.grades.domain.use_case.get_all_grades_for_subject.GetAllGradesForSubjectUseCase
import de.grademanager.feature.grades.domain.use_case.get_grade_ordering.GetGradeOrderingUseCase
import de.grademanager.feature.grades.domain.use_case.restore_grade.RestoreGradeUseCase
import de.grademanager.feature.grades.domain.use_case.update_grade_ordering.UpdateGradeOrderingUseCase
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

    single<CalculateAverageGradeUseCase> {
        CalculateAverageGradeUseCaseImpl()
    }

    single<GetAllGradesForSubjectUseCase> {
        GetAllGradesForSubjectUseCaseImpl(
            gradeRepository = get()
        )
    }

    single<CreateGradeUseCase> {
        CreateGradeUseCaseImpl(
            gradeRepository = get(),
            findSubjectUseCase = get()
        )
    }

    single<DeleteGradeUseCase> {
        DeleteGradeUseCaseImpl(
            gradeRepository = get()
        )
    }

    single<RestoreGradeUseCase> {
        RestoreGradeUseCaseImpl(
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
}
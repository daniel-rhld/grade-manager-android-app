package de.grademanager.core.di

import androidx.room.Room
import de.grademanager.core.data.database.GradeManagerDatabase
import de.grademanager.core.presentation.screens.AppHostViewModel
import de.grademanager.core.presentation.snackbar.SnackbarController
import de.grademanager.feature.subjects.di.SubjectsModule
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val AppModule = module {
    single<GradeManagerDatabase> {
        Room.databaseBuilder(
            context = androidContext(),
            klass = GradeManagerDatabase::class.java,
            name = "grade-manager.db"
        ).build()
    }

    single { SnackbarController() }

    viewModel {
        AppHostViewModel(
            snackbarController = get()
        )
    }

    includes(SubjectsModule)
}
package de.grademanager.core.di

import androidx.room.Room
import de.grademanager.core.data.database.GradeManagerDatabase
import de.grademanager.feature.subjects.di.SubjectsModule
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val AppModule = module {
    single<GradeManagerDatabase> {
        Room.databaseBuilder(
            context = androidContext(),
            klass = GradeManagerDatabase::class.java,
            name = "grade-manager.db"
        ).build()
    }

    includes(SubjectsModule)
}
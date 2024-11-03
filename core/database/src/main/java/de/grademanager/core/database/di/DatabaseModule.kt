package de.grademanager.core.database.di

import androidx.room.Room
import de.grademanager.core.database.GradeManagerDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val DatabaseModule = module {
    single<GradeManagerDatabase> {
        Room.databaseBuilder(
            context = androidContext(),
            klass = GradeManagerDatabase::class.java,
            name = "grade-manager.db"
        ).build()
    }
}
package de.grademanager

import android.app.Application
import de.grademanager.core.data.di.RepositoryModule
import de.grademanager.core.database.di.DaoModule
import de.grademanager.core.database.di.DatabaseModule
import de.grademanager.core.di.AppModule
import de.grademanager.core.domain.di.UseCaseModule
import de.grademanger.core.datastore.di.DataStoreModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class GradeManagerApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@GradeManagerApplication)

            modules(
                DatabaseModule,
                DaoModule,
                DataStoreModule,
                RepositoryModule,
                UseCaseModule
            )

            modules(AppModule)
        }
    }

}
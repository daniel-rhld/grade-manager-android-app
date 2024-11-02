package de.grademanager

import android.app.Application
import de.grademanager.core.di.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class GradeManagerApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@GradeManagerApplication)
            modules(AppModule)
        }
    }

}
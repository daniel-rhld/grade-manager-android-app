package de.grademanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import de.grademanager.core.di.AppModule
import de.grademanager.core.presentation.screens.AppHost
import de.grademanager.core.presentation.theme.GradeManagerTheme
import de.grademanager.feature.subjects.di.SubjectsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        startKoin {
            androidLogger()
            androidContext(this@MainActivity)
            modules(AppModule)
        }

        setContent {
            GradeManagerTheme {
                AppHost()
            }
        }

    }
}
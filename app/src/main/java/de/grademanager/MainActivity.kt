package de.grademanager

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.Color
import de.grademanager.core.di.AppModule
import de.grademanager.core.domain.ext.toColorInt
import de.grademanager.core.presentation.screens.AppHost
import de.grademanager.core.presentation.theme.GradeManagerTheme
import de.grademanager.feature.subjects.di.SubjectsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                lightScrim = Color.Transparent.toColorInt(),
                darkScrim = Color.Transparent.toColorInt()
            ),
            navigationBarStyle = SystemBarStyle.auto(
                lightScrim = Color.Red.toColorInt(),
                darkScrim = Color.Red.toColorInt()
            )
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.isNavigationBarContrastEnforced = false
        }

        super.onCreate(savedInstanceState)

        setContent {
            GradeManagerTheme {
                AppHost()
            }
        }

    }
}
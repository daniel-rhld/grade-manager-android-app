package de.grademanager.core.presentation.effects

import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import de.grademanager.core.domain.ext.toColorInt

@Composable
fun setNavigationBarColor(color: Color) {
    val isDarkTheme = isSystemInDarkTheme()
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        (context as? ComponentActivity)?.enableEdgeToEdge(
            navigationBarStyle = if (isDarkTheme) {
                SystemBarStyle.dark(
                    scrim = color.toColorInt()
                )
            } else {
                SystemBarStyle.light(
                    scrim = color.toColorInt(),
                    darkScrim = color.toColorInt()
                )
            }
        )
    }
}
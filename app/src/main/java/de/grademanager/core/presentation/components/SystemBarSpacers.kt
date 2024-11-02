package de.grademanager.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity

@Composable
fun StatusBarSpacer(color: Color = Color.Transparent) {
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(
                height = with(LocalDensity.current) {
                    WindowInsets.statusBars.getTop(this).toDp()
                }
            ).background(color)
    )
}

@Composable
fun NavigationBarSpacer(color: Color = Color.Transparent) {
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(
                height = with(LocalDensity.current) {
                    WindowInsets.navigationBars.getBottom(this).toDp()
                }
            ).background(color)
    )
}
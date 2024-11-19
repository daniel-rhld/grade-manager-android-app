package de.grademanager.core.ui.extensions

import androidx.annotation.ColorInt
import androidx.compose.ui.graphics.Color

@ColorInt
fun Color.toColorInt() = android.graphics.Color.argb(
    this.alpha, this.red, this.green, this.blue
)

fun @receiver:ColorInt Int.toComposeColor(): Color {
    return Color(this)
}
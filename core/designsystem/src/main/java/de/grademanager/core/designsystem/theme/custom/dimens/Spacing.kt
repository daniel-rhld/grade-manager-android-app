package de.grademanager.core.presentation.theme.custom.dimens

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Stable
class Spacing(
    screenSpacing: Dp,
    bottomDialogContentSpacing: Dp,
    innerCardSpacing: Dp,
    verticalItemSpacing: Dp,
) {
    companion object

    var screenSpacing by mutableStateOf(screenSpacing)
        private set

    var bottomDialogContentSpacing by mutableStateOf(bottomDialogContentSpacing)
        private set

    var innerCardSpacing by mutableStateOf(innerCardSpacing)
        private set

    var verticalItemSpacing by mutableStateOf(verticalItemSpacing)
        private set

    fun update(other: Spacing) {
        screenSpacing = other.screenSpacing
        verticalItemSpacing = other.verticalItemSpacing
    }
}

val Spacing.Companion.phone: Spacing
    get() = Spacing(
        screenSpacing = 24.dp,
        bottomDialogContentSpacing = 20.dp,
        innerCardSpacing = 12.dp,
        verticalItemSpacing = 16.dp
    )
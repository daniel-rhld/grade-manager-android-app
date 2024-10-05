package de.grademanager.core.presentation.theme.custom.dimens

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Stable
class Sizes(
    fabSize: Dp
) {
    companion object

    var fabSize by mutableStateOf(fabSize)
        private set

    fun update(other: Sizes) {
        fabSize = other.fabSize
    }

}

val Sizes.Companion.phone: Sizes
    get() = Sizes(
        fabSize = 56.dp
    )
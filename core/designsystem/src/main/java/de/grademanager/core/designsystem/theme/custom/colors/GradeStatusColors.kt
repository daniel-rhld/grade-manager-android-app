package de.grademanager.core.presentation.theme.custom.colors

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

@Stable
class GradeStatusColors(
    badContainer: Color,
    onBadContainer: Color,

    moderateContainer: Color,
    onModerateContainer: Color,

    goodContainer: Color,
    onGoodContainer: Color
) {
    companion object

    var badContainer by mutableStateOf(badContainer)
        private set
    var onBadContainer by mutableStateOf(onBadContainer)
        private set

    var moderateContainer by mutableStateOf(moderateContainer)
        private set
    var onModerateContainer by mutableStateOf(onModerateContainer)
        private set

    var goodContainer by mutableStateOf(goodContainer)
        private set
    var onGoodContainer by mutableStateOf(onGoodContainer)
        private set

    fun update(other: GradeStatusColors) {
        badContainer = other.badContainer
        onBadContainer = other.onBadContainer
        moderateContainer = other.moderateContainer
        onModerateContainer = other.onModerateContainer
        goodContainer = other.goodContainer
        onGoodContainer = other.onGoodContainer
    }
}

val GradeStatusColors.Companion.lightColorScheme: GradeStatusColors
    get() = GradeStatusColors(
        badContainer = Color(0xFFFF3B30),
        onBadContainer = Color(0xFFFFFFFF),
        moderateContainer = Color(0xFFFF9500),
        onModerateContainer = Color(0xFFFFFFFF),
        goodContainer = Color(0xFF34C759),
        onGoodContainer = Color(0xFFFFFFFF)
    )

val GradeStatusColors.Companion.darkColorScheme: GradeStatusColors
    get() = GradeStatusColors(
        badContainer = Color(0xFFFF453A),
        onBadContainer = Color(0xFFFFFFFF),
        moderateContainer = Color(0xFFFF9D0A),
        onModerateContainer = Color(0xFFFFFFFF),
        goodContainer = Color(0xFF30D158),
        onGoodContainer = Color(0xFFFFFFFF)
    )
package de.grademanager.core.domain.ext

import java.util.Locale

fun Double.formatGrade(): String {
    val decimalPlacesModifier = when {
        this.toBigDecimal() % 1.0.toBigDecimal() == 0.0.toBigDecimal() -> 0
        this.toBigDecimal() % 0.1.toBigDecimal() == 0.0.toBigDecimal() -> 1
        else -> 2
    }

    return String.format(
        locale = Locale.getDefault(),
        format = "%.${decimalPlacesModifier}f",
        this
    )
}

fun Double.formatWeighting(): String {
    val decimalPlacesModifier = when {
        this.toBigDecimal() % 0.1.toBigDecimal() == 0.0.toBigDecimal() -> 1
        else -> 2
    }.toString()

    return String.format(
        locale = Locale.getDefault(),
        format = "%.2f",
        this
    )
}

fun Double.isGoodGrade(): Boolean {
    return this >= 1.0 && this < 2.5
}

fun Double.isModerateGrade(): Boolean {
    return this >= 2.5 && this < 4.5
}

fun Double.isBadGrade(): Boolean {
    return this in 4.5..6.0
}
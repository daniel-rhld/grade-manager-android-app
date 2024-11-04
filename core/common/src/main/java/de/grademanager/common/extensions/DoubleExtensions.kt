package de.grademanager.common.extensions

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
    return String.format(
        locale = Locale.getDefault(),
        format = "%.2f",
        this
    )
}
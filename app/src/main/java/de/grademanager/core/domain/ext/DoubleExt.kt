package de.grademanager.core.domain.ext

import java.util.Locale

fun Double.formatGrade(): String {
    return String.format(
        locale = Locale.getDefault(),
        format = "%.2f",
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

fun Double.isGoodGrade(): Boolean {
    return this < 2.5
}

fun Double.isModerateGrade(): Boolean {
    return this >= 2.5 && this < 4.5
}

fun Double.isBadGrade(): Boolean {
    return this >= 4.5
}
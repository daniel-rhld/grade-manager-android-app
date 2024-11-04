package de.grademanager.common.util

import android.content.Context
import androidx.annotation.StringRes

sealed class StringWrapper {
    data class Value(val value: String) : StringWrapper()
    data class Resource(@StringRes val value: Int) : StringWrapper()
}

fun @receiver:StringRes Int.asStringWrapper(): StringWrapper {
    return StringWrapper.Resource(value = this)
}

fun String.asStringWrapper(): StringWrapper {
    return StringWrapper.Value(value = this)
}

fun StringWrapper.get(context: Context): String {
    return when(this) {
        is StringWrapper.Resource -> context.getString(this.value)
        is StringWrapper.Value -> this.value
    }
}
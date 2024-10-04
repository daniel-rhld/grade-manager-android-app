package de.grademanager.core.data.model

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

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

@Composable
fun StringWrapper.get(): String {
    return when(this) {
        is StringWrapper.Resource -> stringResource(id = this.value)
        is StringWrapper.Value -> this.value
    }
}
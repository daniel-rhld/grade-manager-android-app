package de.grademanager.core.ui.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import de.grademanager.common.util.StringWrapper

@Composable
fun StringWrapper.get(): String {
    return when(this) {
        is StringWrapper.Resource -> stringResource(this.value)
        is StringWrapper.Value -> this.value
    }
}
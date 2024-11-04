package de.grademanager.core.ui.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import de.grademanager.common.util.State
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

typealias ComposeState <T> = androidx.compose.runtime.State<T>

@Composable
fun <T> State<T>.collectAsState(
    context: CoroutineContext = EmptyCoroutineContext
): ComposeState<T> = state.collectAsState(context = context)
package de.grademanager.core.designsystem.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import de.grademanager.common.util.get
import de.grademanager.core.descriptors.SnackbarBuilder
import de.grademanager.core.descriptors.SnackbarDuration
import de.grademanager.core.descriptors.SnackbarType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

typealias AndroidSnackbarDuration = androidx.compose.material3.SnackbarDuration

@Composable
fun AppSnackbarHost(snackbarData: Flow<SnackbarBuilder>) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val errorSnackbarHostState = remember { SnackbarHostState() }
    val infoSnackbarHostState = remember { SnackbarHostState() }

    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(
        lifecycleOwner.lifecycle,
        errorSnackbarHostState,
        infoSnackbarHostState,
        snackbarData
    ) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            withContext(Dispatchers.Main.immediate) {
                snackbarData.collect { builder ->
                    coroutineScope.launch {
                        errorSnackbarHostState.currentSnackbarData?.dismiss()
                        infoSnackbarHostState.currentSnackbarData?.dismiss()

                        when (builder.type) {
                            SnackbarType.ERROR -> errorSnackbarHostState
                            SnackbarType.INFO -> infoSnackbarHostState
                        }.let { snackbarHostState ->
                            val result = snackbarHostState.showSnackbar(
                                message = builder.message.get(context),
                                actionLabel = builder.action?.label?.get(context),
                                withDismissAction = false,
                                duration = when (builder.duration) {
                                    SnackbarDuration.SHORT -> AndroidSnackbarDuration.Short
                                    SnackbarDuration.LONG -> AndroidSnackbarDuration.Long
                                    SnackbarDuration.INDEFINITE -> AndroidSnackbarDuration.Indefinite
                                }
                            )

                            if (result == SnackbarResult.ActionPerformed) {
                                builder.action?.action?.invoke()
                            }
                        }
                    }
                }
            }
        }
    }

    SnackbarHost(
        hostState = errorSnackbarHostState,
        modifier = Modifier.fillMaxWidth(),
        snackbar = {
            DismissibleSnackbar(
                snackbarData = it,
                containerColor = MaterialTheme.colorScheme.error,
                contentColor = MaterialTheme.colorScheme.onError,
                onDismiss = {
                    errorSnackbarHostState.currentSnackbarData?.dismiss()
                }
            )
        }
    )

    SnackbarHost(
        hostState = infoSnackbarHostState,
        modifier = Modifier.fillMaxWidth(),
        snackbar = {
            DismissibleSnackbar(
                snackbarData = it,
                onDismiss = {
                    infoSnackbarHostState.currentSnackbarData?.dismiss()
                }
            )
        }
    )

}
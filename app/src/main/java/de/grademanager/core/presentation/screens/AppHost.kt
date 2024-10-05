package de.grademanager.core.presentation.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.generated.NavGraphs
import com.ramcosta.composedestinations.utils.currentDestinationAsState
import com.ramcosta.composedestinations.utils.rememberDestinationsNavigator
import de.grademanager.core.data.model.get
import de.grademanager.core.presentation.snackbar.DismissibleSnackbar
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppHost() {
    val context = LocalContext.current

    val viewModel: AppHostViewModel = koinViewModel()

    val errorSnackbarHostState = remember { SnackbarHostState() }
    val neutralSnackbarHostState = remember { SnackbarHostState() }

    val errorSnackbarData by viewModel.errorSnackbarData.collectAsState()
    val neutralSnackbarData by viewModel.neutralSnackbarData.collectAsState()

    val currentDestination by rememberNavController().currentDestinationAsState()

    LaunchedEffect(key1 = currentDestination) {
        viewModel.clearErrorSnackbarData()
        viewModel.clearNeutralSnackbarData()

        errorSnackbarHostState.currentSnackbarData?.dismiss()
        neutralSnackbarHostState.currentSnackbarData?.dismiss()
    }

    LaunchedEffect(key1 = errorSnackbarData) {
        errorSnackbarData?.let { data ->
            val result = errorSnackbarHostState.showSnackbar(
                message = data.message.get(context = context),
                withDismissAction = false,
                duration = data.duration,
                actionLabel = data.action?.label?.get(context = context)
            )

            viewModel.clearErrorSnackbarData()

            if (result == SnackbarResult.ActionPerformed) {
                data.action?.action?.invoke()
            }
        }
    }

    LaunchedEffect(key1 = neutralSnackbarData) {
        neutralSnackbarData?.let { data ->
            val result = neutralSnackbarHostState.showSnackbar(
                message = data.message.get(context = context),
                withDismissAction = false,
                duration = data.duration,
                actionLabel = data.action?.label?.get(context = context)
            )

            viewModel.clearNeutralSnackbarData()

            if (result == SnackbarResult.ActionPerformed) {
                data.action?.action?.invoke()
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(
                hostState = errorSnackbarHostState,
                modifier = Modifier.fillMaxWidth(),
                snackbar = { snackbarData ->
                    DismissibleSnackbar(
                        snackbarData = snackbarData,
                        padding = PaddingValues(16.dp),
                        containerColor = MaterialTheme.colorScheme.error,
                        contentColor = MaterialTheme.colorScheme.onError,
                        onDismiss = {
                            errorSnackbarHostState.currentSnackbarData?.dismiss()
                            viewModel.clearErrorSnackbarData()
                        }
                    )
                }
            )

            SnackbarHost(
                hostState = neutralSnackbarHostState,
                modifier = Modifier.fillMaxWidth(),
                snackbar = { snackbarData ->
                    DismissibleSnackbar(
                        snackbarData = snackbarData,
                        padding = PaddingValues(16.dp),
                        onDismiss = {
                            neutralSnackbarHostState.currentSnackbarData?.dismiss()
                            viewModel.clearNeutralSnackbarData()
                        }
                    )
                }
            )
        }
    ) { padding ->
        DestinationsNavHost(
            navGraph = NavGraphs.root,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        )
    }
}
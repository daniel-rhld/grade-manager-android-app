package de.grademanager.core.presentation.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.generated.NavGraphs

@Composable
fun AppHost() {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { padding ->
        DestinationsNavHost(
            navGraph = NavGraphs.root,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        )
    }
}
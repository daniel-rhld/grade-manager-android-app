package de.grademanager

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import de.grademanager.core.designsystem.components.AppSnackbarHost
import de.grademanager.core.domain.controller.snackbar.SnackbarController
import de.grademanager.feature.subjects.detail.SubjectDetailScreen
import de.grademanager.feature.subjects.detail.SubjectDetailScreenDestination
import de.grademanager.feature.subjects.overview.SubjectsOverviewScreen
import de.grademanager.feature.subjects.overview.SubjectsOverviewScreenDestination
import org.koin.compose.koinInject

@Composable
fun GradeManagerHost() {
    val navController = rememberNavController()
    val snackbarController: SnackbarController = koinInject()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        snackbarHost = {
            AppSnackbarHost(snackbarController.snackbarData)
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = SubjectsOverviewScreenDestination,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            composable<SubjectsOverviewScreenDestination> {
                SubjectsOverviewScreen(
                    navigateToSubjectRequested = { subjectId ->
                        navController.navigate(
                            SubjectDetailScreenDestination(
                                subjectId = subjectId,
                                subjectName = ""
                            )
                        )
                    }
                )
            }

            composable<SubjectDetailScreenDestination> { backStackEntry ->
                val navArgs = backStackEntry.toRoute<SubjectDetailScreenDestination>()

                SubjectDetailScreen(
                    subjectId = navArgs.subjectId,
                    subjectName = navArgs.subjectName,
                    navController = navController
                )
            }
        }
    }
}
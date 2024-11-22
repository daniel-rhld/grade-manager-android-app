package de.grademanager

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import de.grademanager.feature.auth.login.LoginScreen
import de.grademanager.feature.auth.login.LoginScreenDestination
import de.grademanager.feature.subjects.detail.SubjectDetailScreen
import de.grademanager.feature.subjects.detail.SubjectDetailScreenDestination
import de.grademanager.feature.subjects.overview.SubjectsOverviewScreen
import de.grademanager.feature.subjects.overview.SubjectsOverviewScreenDestination

@Composable
fun GradeManagerHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = LoginScreenDestination,
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
    ) {
        composable<LoginScreenDestination> {
            LoginScreen(
                navigateToRegistrationRequested = {
                    // TODO: Navigate to registration
                },
                navigateToSubjectOverviewRequested = {
                    navController.navigate(
                        SubjectsOverviewScreenDestination
                    )
                }
            )
        }

        composable<SubjectsOverviewScreenDestination> {
            SubjectsOverviewScreen(
                navigateToSubjectRequested = { subjectId ->
                    navController.navigate(
                        SubjectDetailScreenDestination(
                            subjectId = subjectId
                        )
                    )
                }
            )
        }

        composable<SubjectDetailScreenDestination> { backStackEntry ->
            val navArgs = backStackEntry.toRoute<SubjectDetailScreenDestination>()

            SubjectDetailScreen(
                subjectId = navArgs.subjectId,
                navController = navController
            )
        }
    }
}
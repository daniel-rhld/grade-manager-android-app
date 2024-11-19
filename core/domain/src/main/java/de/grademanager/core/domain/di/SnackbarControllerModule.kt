package de.grademanager.core.domain.di

import de.grademanager.core.domain.controller.snackbar.SnackbarController
import de.grademanager.core.domain.controller.snackbar.SnackbarControllerImpl
import org.koin.dsl.module

val SnackbarControllerModule = module {
    single<SnackbarController> {
        SnackbarControllerImpl()
    }
}
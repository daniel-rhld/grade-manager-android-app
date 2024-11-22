package de.grademanager.feature.auth.login.di

import de.grademanager.feature.auth.login.LoginViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val LoginModule = module {
    viewModel {
        LoginViewModel(
            validateLoginFormUseCase = get(),
            loginUseCase = get(),
            snackbarController = get()
        )
    }
}
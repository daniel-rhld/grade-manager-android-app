package de.grademanager.feature.auth.register.di

import de.grademanager.feature.auth.register.RegisterViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val RegisterModule = module {
    viewModel {
        RegisterViewModel(
            registerUseCase = get(),
            loginUseCase = get(),
            validateRegisterFormUseCase = get(),
            snackbarController = get()
        )
    }
}
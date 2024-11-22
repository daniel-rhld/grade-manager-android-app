package de.grademanager.core.domain.use_case.login

import de.grademanager.core.data.dto.login.LoginResponse
import de.grademanager.core.network.RequestState
import kotlinx.coroutines.flow.Flow

interface LoginUseCase {

    suspend operator fun invoke(
        emailAddress: String,
        password: String
    ): Flow<RequestState<LoginResponse>>

}
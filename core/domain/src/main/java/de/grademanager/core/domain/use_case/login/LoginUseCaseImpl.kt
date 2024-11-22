package de.grademanager.core.domain.use_case.login

import de.grademanager.core.data.dto.login.LoginResponse
import de.grademanager.core.data.repository.auth.AuthRepository
import de.grademanager.core.network.RequestState
import kotlinx.coroutines.flow.Flow

class LoginUseCaseImpl(
    private val authRepository: AuthRepository
) : LoginUseCase {

    override suspend fun invoke(
        emailAddress: String,
        password: String
    ): Flow<RequestState<LoginResponse>> {
        return authRepository.login(
            emailAddress = emailAddress,
            password = password
        )
    }

}
package de.grademanager.core.domain.use_case.register

import de.grademanager.core.data.dto.common.SuccessResponse
import de.grademanager.core.data.repository.auth.AuthRepository
import de.grademanager.core.network.RequestState
import kotlinx.coroutines.flow.Flow

class RegisterUseCaseImpl(
    private val authRepository: AuthRepository
) : RegisterUseCase {

    override suspend fun invoke(
        firstName: String,
        lastName: String,
        emailAddress: String,
        password: String,
        passwordConfirmation: String
    ): Flow<RequestState<SuccessResponse>> {
        return authRepository.register(
            firstName = firstName,
            lastName = lastName,
            emailAddress = emailAddress,
            password = password,
            passwordConfirmation = passwordConfirmation
        )
    }

}
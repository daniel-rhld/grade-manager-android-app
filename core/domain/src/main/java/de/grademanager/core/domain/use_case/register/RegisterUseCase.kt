package de.grademanager.core.domain.use_case.register

import de.grademanager.core.data.dto.common.SuccessResponse
import de.grademanager.core.network.RequestState
import kotlinx.coroutines.flow.Flow

interface RegisterUseCase {

    suspend operator fun invoke(
        firstName: String,
        lastName: String,
        emailAddress: String,
        password: String,
        passwordConfirmation: String
    ): Flow<RequestState<SuccessResponse>>

}
package de.grademanager.core.data.repository.auth

import de.grademanager.core.data.dto.common.SuccessResponse
import de.grademanager.core.data.dto.login.LoginResponse
import de.grademanager.core.network.RequestState
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun register(
        firstName: String,
        lastName: String,
        emailAddress: String,
        password: String,
        passwordConfirmation: String
    ): Flow<RequestState<SuccessResponse>>

    suspend fun login(
        emailAddress: String,
        password: String
    ): Flow<RequestState<LoginResponse>>

}
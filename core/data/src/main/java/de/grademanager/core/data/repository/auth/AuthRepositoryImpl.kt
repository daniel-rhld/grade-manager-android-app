package de.grademanager.core.data.repository.auth

import androidx.datastore.core.DataStore
import de.grademanager.core.data.dto.common.SuccessResponse
import de.grademanager.core.data.dto.login.LoginBody
import de.grademanager.core.data.dto.login.LoginResponse
import de.grademanager.core.data.dto.register.RegisterBody
import de.grademanager.core.data.dto.user.asDataStore
import de.grademanager.core.network.RequestState
import de.grademanager.core.network.SessionManager
import de.grademanager.core.network.networkCall
import de.grademanger.core.datastore.model.user.UserDataStore
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import kotlinx.coroutines.flow.Flow

class AuthRepositoryImpl(
    private val httpClient: HttpClient,
    private val sessionManager: SessionManager,
    private val userDataStore: DataStore<UserDataStore>
) : AuthRepository {

    override suspend fun register(
        firstName: String,
        lastName: String,
        emailAddress: String,
        password: String,
        passwordConfirmation: String
    ): Flow<RequestState<SuccessResponse>> {
        return networkCall(
            tag = "register",
            call = {
                httpClient.post {
                    url("/api/auth/register/")
                    setBody(
                        body = RegisterBody(
                            firstName = firstName,
                            lastName = lastName,
                            emailAddress = emailAddress,
                            password = password,
                            passwordConfirmation = passwordConfirmation
                        )
                    )
                }
            }
        )
    }

    override suspend fun login(
        emailAddress: String,
        password: String
    ): Flow<RequestState<LoginResponse>> {
        return networkCall(
            tag = "login",
            call = {
                httpClient.post {
                    url("/api/auth/login/")
                    setBody(
                        body = LoginBody(
                            emailAddress = emailAddress,
                            password = password
                        )
                    )
                }
            },
            onSuccess = { response ->
                sessionManager.setAccessToken(response.accessToken.token)
                sessionManager.setRefreshToken(response.refreshToken)

                userDataStore.updateData { response.user.asDataStore() }
            }
        )
    }

}
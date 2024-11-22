package de.grademanager.core.data.dto.login

import de.grademanager.core.data.dto.user.UserResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    @SerialName("access_token")
    val accessToken: AccessToken,

    @SerialName("refresh_token")
    val refreshToken: String,

    @SerialName("user")
    val user: UserResponse
) {
    @Serializable
    data class AccessToken(
        @SerialName("token")
        val token: String,

        @SerialName("expiration")
        val expirationTimestamp: Long
    )
}

package de.grademanager.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RefreshAccessTokenResponse(
    @SerialName("access_token")
    val accessToken: AccessToken,

    @SerialName("refresh_token")
    val refreshToken: String,
) {
    @Serializable
    data class AccessToken(
        @SerialName("token")
        val token: String,

        @SerialName("expiration")
        val expirationTimestamp: Long
    )
}

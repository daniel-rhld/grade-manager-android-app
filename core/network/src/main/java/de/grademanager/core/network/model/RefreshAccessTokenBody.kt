package de.grademanager.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RefreshAccessTokenBody(
    @SerialName("refresh_token")
    val refreshToken: String
)

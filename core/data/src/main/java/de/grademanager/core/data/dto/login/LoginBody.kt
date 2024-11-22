package de.grademanager.core.data.dto.login

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginBody(
    @SerialName("email_address")
    val emailAddress: String,

    @SerialName("password")
    val password: String
)

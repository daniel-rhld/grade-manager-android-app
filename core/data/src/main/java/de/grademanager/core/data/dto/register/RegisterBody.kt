package de.grademanager.core.data.dto.register

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterBody(
    @SerialName("first_name")
    val firstName: String,

    @SerialName("last_name")
    val lastName: String,

    @SerialName("email_address")
    val emailAddress: String,

    @SerialName("password")
    val password: String,

    @SerialName("password_confirmation")
    val passwordConfirmation: String
)

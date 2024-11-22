package de.grademanager.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MessageResponse(
    @SerialName("success")
    val success: Boolean,

    @SerialName("message")
    val message: String?
)
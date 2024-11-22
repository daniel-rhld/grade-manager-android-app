package de.grademanager.core.data.dto.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SuccessResponse(
    @SerialName("success")
    val success: Boolean,

    @SerialName("message")
    val message: String?
)

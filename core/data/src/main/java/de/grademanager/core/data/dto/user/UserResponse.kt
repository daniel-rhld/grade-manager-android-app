package de.grademanager.core.data.dto.user

import de.grademanger.core.datastore.model.user.UserDataStore
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    @SerialName("firstname")
    val firstName: String,

    @SerialName("lastname")
    val lastName: String,

    @SerialName("email_address")
    val emailAddress: String,

    @SerialName("profile_picture_url")
    val profilePictureUrl: String,

    @SerialName("email_verified")
    val emailVerified: Boolean
)

fun UserResponse.asDataStore() = UserDataStore(
    isLoggedIn = true,
    firstName = firstName,
    lastName = lastName,
    emailAddress = emailAddress,
    profilePictureUrl = profilePictureUrl,
    emailVerified = emailVerified
)
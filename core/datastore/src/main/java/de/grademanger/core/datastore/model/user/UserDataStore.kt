package de.grademanger.core.datastore.model.user

import de.grademanager.core.model.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDataStore(
    @SerialName("is_logged_in")
    val isLoggedIn: Boolean,

    @SerialName("firstname")
    val firstName: String = "",

    @SerialName("lastname")
    val lastName: String = "",

    @SerialName("email_address")
    val emailAddress: String = "",

    @SerialName("profile_picture_url")
    val profilePictureUrl: String = "",

    @SerialName("email_verified")
    val emailVerified: Boolean = false
)

fun UserDataStore.asExternalModel() = User(
    firstName = firstName,
    lastName = lastName,
    emailAddress = emailAddress,
    profilePictureUrl = profilePictureUrl,
    emailVerified = emailVerified
)
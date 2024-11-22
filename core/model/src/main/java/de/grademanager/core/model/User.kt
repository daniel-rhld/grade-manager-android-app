package de.grademanager.core.model

data class User(
    val firstName: String,
    val lastName: String,
    val emailAddress: String,
    val profilePictureUrl: String,
    val emailVerified: Boolean
)

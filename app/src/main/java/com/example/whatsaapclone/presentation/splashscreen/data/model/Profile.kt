package com.example.whatsaapclone.presentation.splashscreen.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Profile(
    val id: String,
    val phone: String?,
    val username: String?
)
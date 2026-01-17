package com.example.whatsaapclone.presentation.splashscreen.data.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class Message(
    val id: Long = 0,
    val content: String, // Will hold Text OR Image URL
    val type: String = "text", // "text" or "image"
    @SerialName("sender_id") val senderId: String,
    @SerialName("receiver_id") val receiverId: String
)

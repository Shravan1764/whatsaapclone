package com.example.whatsaapclone.presentation.splashscreen.navigation

import kotlinx.serialization.Serializable

sealed class routes {

    @Serializable
    data object splashscreen: routes()

    @Serializable
    data object WelcomeScreen : routes()

    @Serializable
    data object userregistration : routes()

    @Serializable
    data object mainscreen : routes()

    @Serializable
    data class VerifyOtp(val phoneNumber: String) : routes()
    @Serializable
    data class ChatScreen(val userId: String) : routes()




//    @Serializable
//    data object homescreen : routes()
//
//    @Serializable
//    data object updatesScreen : routes()
//
//    @Serializable
//    data object communityscreen : routes()
//
//    @Serializable
//    data object calls : routes()

}
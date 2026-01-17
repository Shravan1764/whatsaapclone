package com.example.whatsaapclone.presentation.splashscreen.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.whatsaapclone.presentation.splashscreen.WhatsAppMainScreen
import com.example.whatsaapclone.presentation.splashscreen.calling.CallsScreen
import com.example.whatsaapclone.presentation.splashscreen.chats.ChatScreen
import com.example.whatsaapclone.presentation.splashscreen.community.CommunitiesScreen
import com.example.whatsaapclone.presentation.splashscreen.homescreen.WhatsAppChatListScreen
import com.example.whatsaapclone.presentation.splashscreen.navigation.routes.VerifyOtp
import com.example.whatsaapclone.presentation.splashscreen.navigation.routes.userregistration
import com.example.whatsaapclone.presentation.splashscreen.splashscreen
import com.example.whatsaapclone.presentation.splashscreen.updates.UpdatesScreen
import com.example.whatsaapclone.presentation.splashscreen.userregistration.UserRegistrationScreen
import com.example.whatsaapclone.presentation.splashscreen.userregistration.VerifyOtpScreen
import com.example.whatsaapclone.presentation.splashscreen.welcomescreen.Welcome

@Composable
fun navsystem(){

    val navController = rememberNavController()

    NavHost(startDestination = routes.splashscreen, navController = navController){
        composable<routes.splashscreen>{
            splashscreen(navController)
        }
        composable<routes.WelcomeScreen>{
            Welcome(navController)
        }
        composable<routes.userregistration>{
            UserRegistrationScreen(navController)
        }
        composable<routes.mainscreen>{
            WhatsAppMainScreen(navController)
        }
        composable<routes.VerifyOtp> { backStackEntry ->
            // 1. Extract the argument from the route
            val args = backStackEntry.toRoute<routes.VerifyOtp>()

            // 2. Pass it to the screen
            VerifyOtpScreen(
                navController = navController,
                phoneNumber = args.phoneNumber // Pass this to your UI
            )
        }
        // Inside NavHost...
        composable<routes.ChatScreen> { backStackEntry ->
            val args = backStackEntry.toRoute<routes.ChatScreen>()
            // We will build this screen in the next step
            Text("Chatting with user: ${args.userId}")
        }
        composable<routes.ChatScreen> { backStackEntry ->
            val args = backStackEntry.toRoute<routes.ChatScreen>()

            ChatScreen(
                navController = navController,
                otherUserId = args.userId // Pass the ID from the previous screen
            )
        }

//        composable<routes.updatesScreen>{
//            UpdatesScreen()
//        }
//        composable<routes.communityscreen>{
//            CommunitiesScreen()
//        }
//        composable<routes.calls>{
//            CallsScreen()
//        }

    }
}
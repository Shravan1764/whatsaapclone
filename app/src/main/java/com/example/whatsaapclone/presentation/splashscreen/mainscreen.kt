package com.example.whatsaapclone.presentation.splashscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.whatsaapclone.WhatsAppBottomBar
import com.example.whatsaapclone.presentation.splashscreen.calling.CallsScreen
import com.example.whatsaapclone.presentation.splashscreen.community.CommunitiesScreen
import com.example.whatsaapclone.presentation.splashscreen.homescreen.WhatsAppChatListScreen
import com.example.whatsaapclone.presentation.splashscreen.updates.UpdatesScreen

@Composable
fun WhatsAppMainScreen(navController: NavHostController) {
    // 1. State to track the currently selected tab (0=Chats, 1=Updates, etc.)
    var selectedTab by remember { mutableIntStateOf(0) }

    Scaffold(
        // 2. The Bottom Navigation Bar stays visible at all times
        bottomBar = {
            WhatsAppBottomBar(
                selectedTab = selectedTab,
                onTabSelected = { newIndex -> selectedTab = newIndex }
            )
        }
    ) { paddingValues ->
        // 3. Content Area: Switches based on 'selectedTab'
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (selectedTab) {
                0 -> WhatsAppChatListScreen(navController = navController) // Your Chat List (Home)
                1 -> UpdatesScreen()         // Status & Channels
                2 -> CommunitiesScreen()      // Communities
                3 -> CallsScreen()            // Calls History
            }
        }
    }
}

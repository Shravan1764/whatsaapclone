package com.example.whatsaapclone

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Update
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.Update
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WhatsAppBottomBar(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    // Define the navigation items (Label, Selected Icon, Unselected Icon)
    val items = listOf("Chats", "Updates", "Communities", "Calls")
    val selectedIcons = listOf(
        Icons.Filled.Chat,
        Icons.Filled.Update,
        Icons.Filled.Groups,
        Icons.Filled.Call
    )
    val unselectedIcons = listOf(
        Icons.Outlined.Chat,
        Icons.Outlined.Update,
        Icons.Outlined.Groups,
        Icons.Outlined.Call
    )

    NavigationBar(
        containerColor = Color.White,
        //tonalElevation = 8.dp,
        modifier = Modifier.height(90.dp) // Taller bar for modern look
    ) {
        items.forEachIndexed { index, title ->
            val isSelected = selectedTab == index

            NavigationBarItem(
                selected = isSelected,
                onClick = { onTabSelected(index) },
                icon = {
                    Icon(
                        imageVector = if (isSelected) selectedIcons[index] else unselectedIcons[index],
                        contentDescription = title,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = {
                    Text(
                        text = title,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF075E54), // WhatsApp Dark Green
                    selectedTextColor = Color(0xFF075E54),
                    indicatorColor = Color(0xFFD8FDD2),    // Light Green Pill Background
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray
                )
            )
        }
    }
}

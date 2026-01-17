package com.example.whatsaapclone.presentation.splashscreen.homescreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.whatsaapclone.presentation.splashscreen.navigation.routes

@Composable
fun WhatsAppChatListScreen(navController: NavController) {

    val viewModel: HomeViewModel = viewModel()
    val users = viewModel.userList.value

    if (viewModel.isLoading.value) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(users) { user ->
                UserItem(
                    name = user.username ?: user.phone ?: "Unknown",
                    onClick = {
                        // When clicked, navigate to Chat Screen passing the Other User's ID
                        // We will create this route next!
                        navController.navigate(routes.ChatScreen(user.id))
                    }
                )
            }
        }
    }
}

@Composable
fun UserItem(name: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Simple Avatar Placeholder
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            color = MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier.size(50.dp)
        ) {
            Icon(Icons.Default.Person, contentDescription = null, modifier = Modifier.padding(10.dp))
        }

        Spacer(modifier = Modifier.width(16.dp))

        Text(text = name, fontSize = 18.sp)
    }
}

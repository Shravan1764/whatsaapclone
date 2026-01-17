package com.example.whatsaapclone.presentation.splashscreen.chats

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext // Import needed
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.whatsaapclone.presentation.splashscreen.data.SupabaseClient
import com.example.whatsaapclone.presentation.splashscreen.data.model.Message
import io.github.jan.supabase.auth.auth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(navController: NavController, otherUserId: String) {

    val viewModel: ChatViewModel = viewModel()
    val context = LocalContext.current // 1. Get Context here

    // Load data when screen opens
    LaunchedEffect(otherUserId) {
        viewModel.loadMessages(otherUserId)
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        // 2. Pass context correctly
        uri?.let { viewModel.uploadAndSendImage(it, otherUserId, context) }
    }

    var textState by remember { mutableStateOf("") }
    val myId = SupabaseClient.client.auth.currentUserOrNull()?.id

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Chat") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF008069),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(bottom = 16.dp)
        ) {
            // 1. Message List
            LazyColumn(
                modifier = Modifier.weight(1f).padding(horizontal = 8.dp),
                reverseLayout = false
            ) {
                items(viewModel.messages) { message ->
                    // 3. Pass the full Message object
                    MessageBubble(
                        message = message,
                        isMe = message.senderId == myId
                    )
                }
            }

            // 2. Input Area
            Row(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = textState,
                    onValueChange = { textState = it },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Message") },
                    shape = RoundedCornerShape(24.dp),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )

                // Attachment Button
                IconButton(onClick = { launcher.launch("image/*") }) {
                    Icon(Icons.Default.AttachFile, contentDescription = "Send Image")
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Send Button
                FloatingActionButton(
                    onClick = {
                        viewModel.sendMessage(textState, otherUserId)
                        textState = ""
                    },
                    containerColor = Color(0xFF008069),
                    contentColor = Color.White
                ) {
                    Icon(Icons.Default.Send, contentDescription = "Send")
                }
            }
        }
    }
}

@Composable
fun MessageBubble(message: Message, isMe: Boolean) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = if (isMe) Alignment.CenterEnd else Alignment.CenterStart
    ) {
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = if (isMe) Color(0xFFDCF8C6) else Color.White,
            shadowElevation = 1.dp,
            modifier = Modifier
                .padding(vertical = 4.dp)
                .widthIn(max = 250.dp)
        ) {
            // 4. Handle Text vs Image logic inside the bubble surface
            Column(modifier = Modifier.padding(5.dp)) {
                if (message.type == "image") {
                    AsyncImage(
                        model = message.content,
                        contentDescription = "Sent image",
                        modifier = Modifier
                            .size(200.dp)
                            .padding(bottom = 4.dp)
                    )
                } else {
                    Text(
                        text = message.content,
                        modifier = Modifier.padding(8.dp),
                        color = Color.Black
                    )
                }
            }
        }
    }
}

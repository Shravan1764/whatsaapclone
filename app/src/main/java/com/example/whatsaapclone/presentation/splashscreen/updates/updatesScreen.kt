package com.example.whatsaapclone.presentation.splashscreen.updates
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

// Data Model
data class StatusUpdate(val id: Int, val name: String, val time: String, val imageUrl: String)

@Composable
fun UpdatesScreen() {
    // --- YOUR REQUESTED DATA ---
    val recentUpdates = listOf(
        StatusUpdate(1, "Kartik Aaryan", "Just now", "https://randomuser.me/api/portraits/men/1.jpg"),
        StatusUpdate(2, "Ashish Chanchlani", "10 minutes ago", "https://randomuser.me/api/portraits/men/2.jpg"),
        StatusUpdate(3, "Carryminati", "1 hour ago", "https://randomuser.me/api/portraits/men/3.jpg")
    )

    val channels = listOf(
        StatusUpdate(1, "Developers", "Latest news in tech.", "https://randomuser.me/api/portraits/lego/1.jpg"),
        StatusUpdate(2, "Food Lovers", "Discover new recipes", "https://randomuser.me/api/portraits/lego/2.jpg")
    )

    Scaffold(
        topBar = { UpdatesTopBar() },
        floatingActionButton = { UpdatesFABs() }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
        ) {
            // Section: Status Header
            item {
                Text(
                    text = "Status",
                    fontWeight = FontWeight.Bold,
                    fontSize = 19.sp,
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 12.dp)
                )
            }

            // Section: My Status
            item {
                MyStatusItem()
            }

            // Section: Recent Updates List
            items(recentUpdates) { status ->
                StatusItem(status)
            }

            // Section: Channels Header & Description
            item {
                Divider(color = Color(0xFFF0F2F5), thickness = 1.dp, modifier = Modifier.padding(vertical = 10.dp))

                Text(
                    text = "Channels",
                    fontWeight = FontWeight.Bold,
                    fontSize = 19.sp,
                    modifier = Modifier.padding(start = 16.dp, top = 10.dp)
                )

                Text(
                    text = "Stay updated on topics that matter to you. Find channels to follow below.",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 20.dp)
                )

                Text(
                    text = "Find channels to follow",
                    fontSize = 13.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(start = 16.dp, bottom = 16.dp)
                )
            }

            // Section: Channel List
            items(channels) { channel ->
                ChannelItem(channel)
            }

            // Bottom Spacer
            item { Spacer(modifier = Modifier.height(100.dp)) }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatesTopBar() {
    TopAppBar(
        title = {
            Text("Updates", fontWeight = FontWeight.Bold, fontSize = 22.sp)
        },
        actions = {
            IconButton(onClick = {}) { Icon(Icons.Default.CameraAlt, "Camera") }
            IconButton(onClick = {}) { Icon(Icons.Default.Search, "Search") }
            IconButton(onClick = {}) { Icon(Icons.Default.MoreVert, "More") }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
    )
}

@Composable
fun MyStatusItem() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box {
            AsyncImage(
                model = "https://randomuser.me/api/portraits/men/99.jpg", // My Pic
                contentDescription = "My Status",
                modifier = Modifier
                    .size(54.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            // Plus Icon Badge (Green circle, white icon)
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Status",
                tint = Color.White,
                modifier = Modifier
                    .size(22.dp)
                    .align(Alignment.BottomEnd)
                    .clip(CircleShape)
                    .background(Color(0xFF25D366))
                    .border(2.dp, Color.White, CircleShape)
            )
        }
        Column(modifier = Modifier.padding(start = 16.dp)) {
            Text("My Status", fontWeight = FontWeight.Bold, fontSize = 17.sp)
            Text("Tap to add status update", color = Color.Gray, fontSize = 14.sp)
        }
    }
}

@Composable
fun StatusItem(status: StatusUpdate) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Status Ring (Green Border)
        Box(
            modifier = Modifier
                .size(58.dp)
                .border(2.dp, Color(0xFF25D366), CircleShape) // Green Ring
                .padding(3.dp) // Spacing
        ) {
            AsyncImage(
                model = status.imageUrl,
                contentDescription = status.name,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }

        Column(modifier = Modifier.padding(start = 16.dp)) {
            Text(status.name, fontWeight = FontWeight.Bold, fontSize = 17.sp)
            Text(status.time, color = Color.Gray, fontSize = 14.sp)
        }
    }
}

@Composable
fun ChannelItem(channel: StatusUpdate) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = channel.imageUrl,
            contentDescription = channel.name,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Column(modifier = Modifier.weight(1f).padding(horizontal = 16.dp)) {
            Text(channel.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(channel.time, color = Color.Gray, fontSize = 14.sp, maxLines = 1)
        }

        // Follow Button
        Button(
            onClick = { },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFE9FCD4), // Light Green button bg
                contentColor = Color(0xFF005C4B)    // Dark Green text
            ),
            contentPadding = PaddingValues(horizontal = 16.dp),
            modifier = Modifier.height(36.dp)
        ) {
            Text("Follow", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
fun UpdatesFABs() {
    Column(horizontalAlignment = Alignment.End) {
        SmallFloatingActionButton(
            onClick = { },
            containerColor = Color(0xFFE9FCD4), // Light Green
            contentColor = Color(0xFF075E54),
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Icon(Icons.Default.Edit, "Edit Status")
        }

        FloatingActionButton(
            onClick = { },
            containerColor = Color(0xFF25D366), // Main WhatsApp Green
            contentColor = Color.White
        ) {
            Icon(Icons.Default.CameraAlt, "Camera Status")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WhatsAppPreview() {
    UpdatesScreen()
}
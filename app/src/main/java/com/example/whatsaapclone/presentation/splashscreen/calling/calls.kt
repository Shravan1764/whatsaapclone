package com.example.whatsaapclone.presentation.splashscreen.calling

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
//import androidx.compose.material.icons.filled.AddCall
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Call
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
import com.example.whatsaapclone.presentation.splashscreen.community.CommunitiesScreen

// --- Data Models ---
data class FavoriteContact(val id: Int, val name: String, val imageUrl: String)
data class RecentCall(val id: Int, val name: String, val time: String, val imageUrl: String, val isIncoming: Boolean)

@Composable
fun CallsScreen() {
    // --- Sample Data (Matches Screenshot) ---
    val favorites = listOf(
        FavoriteContact(1, "MrBeast", "https://randomuser.me/api/portraits/men/10.jpg"),
        FavoriteContact(2, "Bhuvan Bam", "https://randomuser.me/api/portraits/men/11.jpg"),
        FavoriteContact(3, "Tripti Dimri", "https://randomuser.me/api/portraits/women/12.jpg"),
        FavoriteContact(4, "Akshay Kumar", "https://randomuser.me/api/portraits/men/6.jpg")
    )

    val recentCalls = listOf(
        RecentCall(1, "Salman Khan", "Yesterday, 8:30 PM", "https://randomuser.me/api/portraits/men/5.jpg", true),
        RecentCall(2, "Shah Rukh Khan", "Today, 10:15 AM", "https://randomuser.me/api/portraits/men/3.jpg", false),
        RecentCall(3, "Ashish Chanchlani", "Today, 2:00 PM", "https://randomuser.me/api/portraits/men/2.jpg", true),
        RecentCall(4, "Hrithik Roshan", "Nov, 8:30 PM", "https://randomuser.me/api/portraits/men/7.jpg", false),
        RecentCall(5, "Akshay Kumar", "Oct, 10:15 AM", "https://randomuser.me/api/portraits/men/6.jpg", true)
    )

    Scaffold(
        topBar = { CallsTopBar() },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { },
                containerColor = Color(0xFF25D366), // WhatsApp Green
                contentColor = Color.White,
                shape = RoundedCornerShape(16.dp) // Square-ish FAB
            ) {
                Icon(Icons.Default.Add, contentDescription = "New Call")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
        ) {

            // Section: Favorites Header
            item {
                Text(
                    text = "Favorites",
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp,
                    modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 10.dp)
                )
            }

            // Section: Favorites Horizontal List
            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    items(favorites) { favorite ->
                        FavoriteItem(favorite)
                    }
                }
            }

            // Section: Start New Call Button
            item {
                Spacer(modifier = Modifier.height(24.dp))
                StartCallButton()
            }

            // Section: Recent Calls Header
            item {
                Text(
                    text = "Recent Calls",
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp,
                    modifier = Modifier.padding(16.dp, 24.dp, 16.dp, 10.dp)
                )
            }

            // Section: Call List
            items(recentCalls) { call ->
                CallLogItem(call)
            }

            // Bottom Spacer
            item { Spacer(modifier = Modifier.height(100.dp)) }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CallsTopBar() {
    TopAppBar(
        title = {
            Text("Calls", fontWeight = FontWeight.Bold, fontSize = 22.sp)
        },
        actions = {
            IconButton(onClick = {}) { Icon(Icons.Default.Search, "Search") }
            IconButton(onClick = {}) { Icon(Icons.Default.MoreVert, "More") }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
    )
}

@Composable
fun FavoriteItem(favorite: FavoriteContact) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(60.dp)
    ) {
        AsyncImage(
            model = favorite.imageUrl,
            contentDescription = favorite.name,
            modifier = Modifier
                .size(54.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = favorite.name,
            fontSize = 12.sp,
            maxLines = 1,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun StartCallButton() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(50.dp)
            .background(Color(0xFF25D366), RoundedCornerShape(25.dp)) // Green Pill
            .clip(RoundedCornerShape(25.dp))
            .clickable { }
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center // Centered Text
    ) {
        Text(
            text = "Start a new call",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
    }
}

@Composable
fun CallLogItem(call: RecentCall) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = call.imageUrl,
            contentDescription = call.name,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Column(modifier = Modifier.weight(1f).padding(horizontal = 16.dp)) {
            Text(
                text = call.name,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp,
                color = if (call.isIncoming && call.id == 1) Color.Red else Color.Black // Example logic
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = if (call.isIncoming) Icons.Outlined.Call else Icons.Default.Call, // Just generic icon
                    contentDescription = null,
                    modifier = Modifier.size(14.dp),
                    tint = if (call.isIncoming) Color.Red else Color(0xFF25D366)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = call.time,
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }
        }

        IconButton(onClick = { }) {
            Icon(Icons.Default.Call, contentDescription = "Call", tint = Color.Black)
        }
    }
}
@Preview(showBackground = true)
@Composable
fun WhatsAppPreview() {
    CallsScreen()
}

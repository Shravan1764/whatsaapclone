package com.example.whatsaapclone.presentation.splashscreen.community

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.whatsaapclone.presentation.splashscreen.updates.UpdatesScreen

// Data Model
data class Community(
    val id: Int,
    val name: String,
    val memberCount: String,
    val imageUrl: String
)

@Composable
fun CommunitiesScreen() {
    // Sample Data
    val communityList = listOf(
        Community(1, "Tech Enthusiasts", "256 members", "https://randomuser.me/api/portraits/lego/1.jpg"),
        Community(2, "Photography Lover", "156 members", "https://randomuser.me/api/portraits/lego/5.jpg"),
        Community(3, "Traveller United", "150 members", "https://randomuser.me/api/portraits/lego/8.jpg")
    )

    Scaffold(
        topBar = { CommunitiesTopBar() }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
        ) {
            // Section: Start New Community Button
            item {
                StartCommunityButton()
            }

            // Section: Your Communities Header
            item {
                Text(
                    text = "Your Communities",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(16.dp)
                )
            }

            // Section: Community List
            items(communityList) { community ->
                CommunityItem(community)
            }

            // Bottom Spacer
            item { Spacer(modifier = Modifier.height(100.dp)) }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommunitiesTopBar() {
    TopAppBar(
        title = {
            Text("Communities", fontWeight = FontWeight.Bold, fontSize = 22.sp)
        },
        actions = {
            IconButton(onClick = {}) { Icon(Icons.Default.Search, "Search") }
            IconButton(onClick = {}) { Icon(Icons.Default.MoreVert, "More") }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
    )
}

@Composable
fun StartCommunityButton() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(50.dp)
            .background(Color(0xFF25D366), RoundedCornerShape(25.dp)) // WhatsApp Green Pill
            .clip(RoundedCornerShape(25.dp))
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icon Box (Slightly darker green or transparent)
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(Color.White.copy(alpha = 0.2f), RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add",
                tint = Color.White
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = "Start a new Community",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
    }
}

@Composable
fun CommunityItem(community: Community) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Square-ish Icon (like puzzle piece in screenshot)
        AsyncImage(
            model = community.imageUrl,
            contentDescription = community.name,
            modifier = Modifier
                .size(50.dp)
                .clip(RoundedCornerShape(10.dp)), // Rounded Square
            contentScale = ContentScale.Crop
        )

        Column(modifier = Modifier.padding(start = 16.dp)) {
            Text(
                text = community.name,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Text(
                text = community.memberCount,
                color = Color.Gray,
                fontSize = 14.sp
            )
        }
    }
}
@Preview(showBackground = true)
@Composable
fun WhatsAppPreview() {
    CommunitiesScreen()
}

package com.example.whatsaapclone.presentation.splashscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.whatsaapclone.R
import com.example.whatsaapclone.presentation.splashscreen.data.SupabaseClient
import com.example.whatsaapclone.presentation.splashscreen.navigation.routes
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.delay

@Composable
fun splashscreen(navHostController: NavHostController){

    LaunchedEffect(Unit) {
        delay(1000) // Show logo for 1 second

        // CHECK SESSION HERE inside the coroutine
        val session = SupabaseClient.client.auth.currentSessionOrNull()

        if (session != null) {
            // User Logged In -> Go to Main
            navHostController.navigate(routes.mainscreen) {
                popUpTo<routes.splashscreen> { inclusive = true }
            }
        } else {
            // No User -> Go to Welcome
            navHostController.navigate(routes.WelcomeScreen) {
                popUpTo<routes.splashscreen> { inclusive = true }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize())  {
        Image(painter = painterResource(id =R.drawable.whatsapp_icon), contentDescription = null,
            modifier = Modifier.size(80.dp)
                .align(Alignment.Center))

        Column(modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 15.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {

            Text("from", fontSize = 18.sp,
                color = colorResource(id = R.color.from),
                modifier = Modifier.padding(bottom = 3.dp))

            Row {
                Icon(painter = painterResource(R.drawable.meta), contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .padding(end = 3.dp),
                        tint = colorResource(id = R.color.meta)
                )
                Text("Meta", fontSize = 20.sp, fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.meta))
            }
        }
        
    }
}
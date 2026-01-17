package com.example.whatsaapclone.presentation.splashscreen.userregistration

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.whatsaapclone.R
import com.example.whatsaapclone.presentation.splashscreen.navigation.routes

@Composable
fun VerifyOtpScreen(
    navController: NavController,
    phoneNumber: String // Received from previous screen
) {
    val viewModel: VerifyOtpViewModel = viewModel()
    var otpCode by remember { mutableStateOf("") }

    // Check if login was successful
    if (viewModel.isLoginSuccess.value) {
        // Navigate to Main App and clear back stack
        LaunchedEffect(Unit) {
            navController.navigate(routes.mainscreen    ) {
                popUpTo(routes.WelcomeScreen) { inclusive = true }
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Verifying your number",
            fontSize = 20.sp,
            color = colorResource(R.color.meta2),
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Waiting to automatically detect an SMS sent to")
        Text(text = phoneNumber, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(32.dp))

        // Simple TextField for OTP (For testing)
        // In a real app, you might want 6 separate boxes
        OutlinedTextField(
            value = otpCode,
            onValueChange = { if (it.length <= 6) otpCode = it },
            label = { Text("Enter 6-digit code") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                // Call Supabase to verify
                viewModel.verifyOtp(phoneNumber, otpCode)
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.meta2))
        ) {
            if (viewModel.isLoading.value) {
                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
            } else {
                Text("Verify")
            }
        }
    }
}

package com.example.whatsaapclone.presentation.splashscreen.userregistration

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions // Import added
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext // Import added
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType // Import added
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.whatsaapclone.R
import com.example.whatsaapclone.presentation.splashscreen.navigation.routes

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun UserRegistrationScreen(navHostController: NavHostController) {

    val viewModel: UserRegistrationViewModel = viewModel()
    val context = LocalContext.current // Context for Toasts

    var expanded by remember { mutableStateOf(false) }
    var selectedCountry by remember { mutableStateOf("India") } // Default to India
    var countryCode by remember { mutableStateOf("+91") } // Default to +91
    var phoneNumber by remember { mutableStateOf("") }

    // Map countries to codes
    val countryMap = mapOf(
        "India" to "+91", "USA" to "+1", "China" to "+86", "Japan" to "+81",
        "Germany" to "+49", "France" to "+33", "Italy" to "+39",
        "Spain" to "+34", "Russia" to "+7", "Canada" to "+1"
    )

    // 1. Navigation Effect (Success)
    if (viewModel.navigateToVerify.value) {
        val fullNumber = "$countryCode$phoneNumber"
        // Ensure you don't navigate twice
        LaunchedEffect(Unit) {
            navHostController.navigate(routes.VerifyOtp(fullNumber))
            viewModel.navigateToVerify.value = false
        }
    }

    // 2. Error Handling Effect (Failure)
    LaunchedEffect(viewModel.errorMessage.value) {
        viewModel.errorMessage.value?.let { error ->
            if (error.isNotEmpty()) {
                Toast.makeText(context, error, Toast.LENGTH_LONG).show()
                viewModel.errorMessage.value = null // Reset error
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(top = 35.dp, bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Enter your phone number",
            fontSize = 20.sp,
            color = colorResource(R.color.meta2),
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.size(16.dp))

        Row {
            Text("WhatsApp will need to verify your phone number")
            Spacer(modifier = Modifier.size(4.dp))
            Text("What's", color = colorResource(R.color.meta2))
        }

        Text("my number?", color = colorResource(R.color.meta2))
        Spacer(modifier = Modifier.size(16.dp))

        // Country Selector
        TextButton(
            onClick = { expanded = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(modifier = Modifier.width(230.dp)) {
                Text(
                    text = selectedCountry,
                    modifier = Modifier.align(Alignment.Center),
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.CenterEnd),
                    tint = colorResource(R.color.meta2)
                )
            }
        }
        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 66.dp),
            thickness = 2.dp,
            color = colorResource(R.color.meta2)
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            countryMap.keys.forEach { country ->
                DropdownMenuItem(
                    text = { Text(text = country) },
                    onClick = {
                        selectedCountry = country
                        countryCode = countryMap[country] ?: "+91" // Auto-update code
                        expanded = false
                    }
                )
            }
        }

        Spacer(modifier = Modifier.size(16.dp))

        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                // Country Code Field
                TextField(
                    value = countryCode,
                    onValueChange = {
                        // Allow user to manually edit code, but keep '+'
                        if(it.startsWith("+")) countryCode = it else countryCode = "+$it"
                    },
                    modifier = Modifier.width(80.dp), // Slightly wider
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone), // Number pad
                    textStyle = LocalTextStyle.current.copy(fontSize = 18.sp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedIndicatorColor = colorResource(R.color.meta),
                        focusedIndicatorColor = colorResource(R.color.meta)
                    )
                )

                Spacer(modifier = Modifier.width(8.dp))

                // Phone Number Field
                TextField(
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    placeholder = { Text(text = "phone number") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone), // Number pad
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedIndicatorColor = colorResource(R.color.meta),
                        focusedIndicatorColor = colorResource(R.color.meta)
                    )
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Carrier charges may apply",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = .6f)
        )
        Spacer(modifier = Modifier.height(26.dp))

        Button(
            onClick = {
                if (phoneNumber.isNotEmpty()) {
                    viewModel.sendOtp(countryCode, phoneNumber)
                } else {
                    Toast.makeText(context, "Please enter phone number", Toast.LENGTH_SHORT).show()
                }
            },
            enabled = !viewModel.isLoading.value,
            modifier = Modifier.size(100.dp, 50.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.meta2))
        ) {
            if (viewModel.isLoading.value) {
                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
            } else {
                Text("Next", color = Color.White, fontSize = 16.sp)
            }
        }
    }
}

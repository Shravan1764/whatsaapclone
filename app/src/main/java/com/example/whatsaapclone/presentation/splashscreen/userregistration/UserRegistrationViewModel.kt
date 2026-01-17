package com.example.whatsaapclone.presentation.splashscreen.userregistration

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope // Make sure this matches your actual SupabaseClient package
import com.example.whatsaapclone.presentation.splashscreen.data.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.OTP
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserRegistrationViewModel : ViewModel() {

    // --- STATES ---
    val isLoading = mutableStateOf(false)
    val navigateToVerify = mutableStateOf(false)
    val errorMessage = mutableStateOf<String?>(null)

    // --- FUNCTION CALLED BY THE BUTTON ---
    fun sendOtp(countryCode: String, phoneNumber: String) {
        val fullNumber = "$countryCode$phoneNumber" // Combine +91 and 99999...

        isLoading.value = true

        // Run on IO Thread (Background) to prevent App Freeze
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // The actual Supabase Network Call
                SupabaseClient.client.auth.signInWith(OTP) {
                    phone = fullNumber
                }

                // Success! Update UI
                isLoading.value = false
                navigateToVerify.value = true

            } catch (e: Exception) {
                // Failure! Show Error
                e.printStackTrace()
                isLoading.value = false
                errorMessage.value = "Error: ${e.message ?: "Unknown error"}"
            }
        }
    }
}

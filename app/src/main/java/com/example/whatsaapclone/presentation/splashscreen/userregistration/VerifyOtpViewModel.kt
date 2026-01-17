package com.example.whatsaapclone.presentation.splashscreen.userregistration

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.whatsaapclone.presentation.splashscreen.data.SupabaseClient
import io.github.jan.supabase.auth.OtpType
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.OTP

import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.launch

class VerifyOtpViewModel : ViewModel() {
    var isLoading = mutableStateOf(false)
    var isLoginSuccess = mutableStateOf(false)

    fun verifyOtp(phoneNumber: String, token: String) {
        viewModelScope.launch {
            isLoading.value = true
            try {
                // Verify the code
                SupabaseClient.client.auth.verifyPhoneOtp(
                    phone = phoneNumber,
                    token = token,
                    type = OtpType.Phone.SMS
                )

                // Double check if we are logged in
                if (SupabaseClient.client.auth.currentSessionOrNull() != null) {
                    isLoading.value = false
                    isLoginSuccess.value = true
                }
            } catch (e: Exception) {
                isLoading.value = false
                e.printStackTrace()
            }
        }
    }
}




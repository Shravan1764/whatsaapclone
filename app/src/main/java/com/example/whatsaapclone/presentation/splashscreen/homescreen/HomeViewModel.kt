package com.example.whatsaapclone.presentation.splashscreen.homescreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.whatsaapclone.presentation.splashscreen.data.SupabaseClient
import com.example.whatsaapclone.presentation.splashscreen.data.model.Profile
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    // State to hold the list of users
    val userList = mutableStateOf<List<Profile>>(emptyList())
    val isLoading = mutableStateOf(false)

    // Get current user ID to exclude ourselves from the list
    private val myUserId = SupabaseClient.client.auth.currentUserOrNull()?.id

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            isLoading.value = true
            try {
                // Fetch all profiles from Supabase
                // Note: In a real app, you would filter this by phone numbers in your contacts
                val profiles = SupabaseClient.client
                    .from("profiles")
                    .select()
                    .decodeList<Profile>()

                // Filter out my own profile (don't want to chat with myself)
                userList.value = profiles.filter { it.id != myUserId }

            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading.value = false
            }
        }
    }
}

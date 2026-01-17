package com.example.whatsaapclone.presentation.splashscreen.chats

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.whatsaapclone.presentation.splashscreen.data.SupabaseClient
import com.example.whatsaapclone.presentation.splashscreen.data.model.Message
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.query.Order
import io.github.jan.supabase.realtime.PostgresAction
import io.github.jan.supabase.realtime.channel
import io.github.jan.supabase.realtime.decodeRecord
import io.github.jan.supabase.realtime.postgresChangeFlow
import io.github.jan.supabase.realtime.realtime
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChatViewModel : ViewModel() {

    private val myUserId = SupabaseClient.client.auth.currentUserOrNull()?.id ?: ""

    // We use mutableStateListOf so Compose updates instantly when we add a message
    val messages = mutableStateListOf<Message>()

    fun loadMessages(otherUserId: String) {
        if (myUserId.isEmpty()) return

        viewModelScope.launch(Dispatchers.IO) {
            // 1. Fetch old history
            try {
                val history = SupabaseClient.client.from("messages")
                    .select {
                        filter {
                            // Logic: (sender=Me AND receiver=Him) OR (sender=Him AND receiver=Me)
                            or {
                                and {
                                    eq("sender_id", myUserId)
                                    eq("receiver_id", otherUserId)
                                }
                                and {
                                    eq("sender_id", otherUserId)
                                    eq("receiver_id", myUserId)
                                }
                            }
                        }
                        order("created_at", Order.ASCENDING)
                    }.decodeList<Message>()

                messages.clear()
                messages.addAll(history)

                // 2. Subscribe to Realtime Updates
                subscribeToRealtime(otherUserId)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

// In ChatViewModel.kt

    private suspend fun subscribeToRealtime(otherUserId: String) {
        val channel = SupabaseClient.client.channel("messages")

        // Listen for INSERT events
        val changeFlow = channel.postgresChangeFlow<PostgresAction.Insert>(schema = "public") {
            table = "messages"
        }

        changeFlow.onEach { change ->
            try {
                val newMessage = change.decodeRecord<Message>()

                // Check if this message belongs to our chat
                val isRelevant = (newMessage.senderId == myUserId && newMessage.receiverId == otherUserId) ||
                        (newMessage.senderId == otherUserId && newMessage.receiverId == myUserId)

                if (isRelevant) {
                    // SWITCH TO MAIN THREAD to update UI
                    withContext(Dispatchers.Main) {
                        // Prevent duplicates (Realtime might send back the one we just optimistically added)
                        // We check if a message with same content & sender exists at the end of list
                        val isDuplicate = messages.any {
                            it.id == newMessage.id || (it.content == newMessage.content && it.senderId == newMessage.senderId)
                        }

                        if (!isDuplicate) {
                            messages.add(newMessage)
                        }
                    }
                }
            } catch(e: Exception) {
                e.printStackTrace()
            }
        }.launchIn(viewModelScope) // This runs the flow

        channel.subscribe()
    }


// In ChatViewModel.kt

    fun sendMessage(content: String, otherUserId: String) {
        if (content.isBlank()) return

        // 1. Create the message object locally
        val optimisticMessage = Message(
            id = System.currentTimeMillis(), // Temporary ID so it has a unique key
            content = content,
            senderId = myUserId,
            receiverId = otherUserId,
        )

        // 2. INSTANTLY add to UI (Optimistic Update)
        // This makes it appear immediately without waiting for network
        messages.add(optimisticMessage)

        viewModelScope.launch(Dispatchers.IO) {
            try {
                // 3. Send to Supabase
                // Note: Supabase will return the REAL record with the real ID
                // Ideally, we would replace the temp one, but for now this is fine.
                SupabaseClient.client.from("messages").insert(optimisticMessage)
            } catch (e: Exception) {
                e.printStackTrace()
                // Optional: If failed, remove the message or show error icon
                messages.remove(optimisticMessage)
            }
        }
    }
    // You need a ContentResolver to read the Uri bytes
    fun uploadAndSendImage(uri: Uri, otherUserId: String, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val bytes = context.contentResolver.openInputStream(uri)?.use { it.readBytes() }
                    ?: return@launch

                val fileName = "${System.currentTimeMillis()}_${myUserId}.jpg"
                val bucket = SupabaseClient.client.storage.from("chat-images")

                // 1. Upload
                bucket.upload(fileName, bytes)

                // 2. Get URL
                val imageUrl = bucket.publicUrl(fileName)

                // 3. Send Message (Type = image)
                val msg = Message(
                    content = imageUrl,
                    type = "image", // Important!
                    senderId = myUserId,
                    receiverId = otherUserId
                )
                SupabaseClient.client.from("messages").insert(msg)

            } catch(e: Exception) { e.printStackTrace() }
        }
    }


}

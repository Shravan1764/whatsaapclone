package com.example.whatsaapclone.presentation.splashscreen.data

import android.content.Context
import com.russhwolf.settings.SharedPreferencesSettings
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.FlowType
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage
import io.github.jan.supabase.realtime.Realtime
import io.ktor.client.engine.okhttp.OkHttp

object SupabaseClient {

    private const val SUPABASE_URL = "https://uhikcfsabnklzrvcxeej.supabase.co"
    private const val SUPABASE_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InVoaWtjZnNhYm5rbHpydmN4ZWVqIiwicm9sZSI6ImFub24iLCJpYXQiOjE3Njc1NDQzNjcsImV4cCI6MjA4MzEyMDM2N30.CeOiVg1T276_tKlSKQ4EL0XAhxHaPmm79X9E8gjIw70" // Put your key back here

    lateinit var client: SupabaseClient

    fun initialize(context: Context) {
        client = createSupabaseClient(
        supabaseUrl = SUPABASE_URL,
        supabaseKey = SUPABASE_KEY
         ){
        httpEngine = OkHttp.create()
            install(Auth) {
                // 1. Create the Settings object wrapping Android SharedPreferences
                val sharedPrefs = context.getSharedPreferences("supabase_session", Context.MODE_PRIVATE)
                var settings = SharedPreferencesSettings(sharedPrefs)
                settings = settings
            }
            install(Postgrest)
            install(Storage)
            install(Realtime)
        }
    }
}



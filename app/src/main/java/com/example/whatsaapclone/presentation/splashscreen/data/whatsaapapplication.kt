package com.example.whatsaapclone.presentation.splashscreen.data

import android.app.Application

class WhatsappApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        SupabaseClient.initialize(this)
    }
}
package com.example.whatsaapclone

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.whatsaapclone.presentation.splashscreen.navigation.navsystem
import com.example.whatsaapclone.ui.theme.WhatsaapCloneTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // System Bar Color Fix
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                window.isNavigationBarContrastEnforced = false
            }
            window.navigationBarColor = android.graphics.Color.TRANSPARENT

            WhatsaapCloneTheme {
                navsystem()
            }
        }
    }
}

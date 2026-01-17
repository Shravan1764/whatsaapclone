package com.example.whatsaapclone.presentation.splashscreen.welcomescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.whatsaapclone.R
import com.example.whatsaapclone.presentation.splashscreen.navigation.routes

@Composable
fun Welcome(navHostController: NavHostController){

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center
                        , horizontalAlignment = Alignment.CenterHorizontally) {

        Image(painter = painterResource(R.drawable.whatsapp_sticker), contentDescription = null
                , modifier = Modifier.size(300.dp))

        Text("Welcome To Whatsaap", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.size(8.dp))

        Row {
            Text("Read our", color = Color.Gray)
            Spacer(modifier = Modifier.size(5.dp))
            Text(" Privacy Policy", color = colorResource(R.color.meta))
            Spacer(modifier = Modifier.size(5.dp))
            Text("Tap 'Agree and continue' to ", color = Color.Gray)
            Spacer(modifier = Modifier.size(5.dp))
        }
        Row {
            Text("accept the " , color = Color.Gray)
            Spacer(modifier = Modifier.size(5.dp))
            Text("Terms of Service", color = colorResource(R.color.meta))
        }
        Spacer(modifier = Modifier.size(24.dp))

        Button(onClick = {navHostController.navigate(routes.userregistration)},modifier = Modifier.size(280.dp, 50.dp), shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.meta2)),
            ) {
            Text("Agree and Continue", color = Color.White, fontSize = 16.sp)
        }
    }

}
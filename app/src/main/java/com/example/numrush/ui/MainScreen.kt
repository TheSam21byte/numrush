package com.example.numrush.ui

import com.example.numrush.R
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.Modifier
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.foundation.Image
import androidx.navigation.NavHostController
import com.example.numrush.ui.navigation.Routes

@Composable
fun MainScreen(navController: NavHostController) {
    // Colores definidos directamente

    val background = Color(0xFF18222F)
    val primaryText = Color(0xFFEADFA1)
    val secondaryText = Color(0xFFBFC7D1)
    val primaryButton = Color(0xFFC9B560)
    val secondaryButton = Color(0xFF546A82)

    // Box Principal

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
            .padding(24.dp),
        contentAlignment = Alignment.TopCenter
    ) {

        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier.fillMaxWidth()
        ) {

            Spacer(modifier = Modifier.height(40.dp))

            // Header principal
            Text(
                text = "NumRush",
                fontSize = 42.sp,
                fontWeight = FontWeight.Bold,
                color = primaryText
            )

            // Subtítulo divertido
            Text(
                text = "¡Adivina el número secreto!",
                fontSize = 18.sp,
                color = secondaryText
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Icono del app
            Image(
                painter = painterResource(id = R.mipmap.ic_launcher_foreground),
                contentDescription = "App Icon",
                modifier = Modifier
                    .size(160.dp)
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Botón principal
            Button (
                onClick = {
                    navController.navigate(Routes.GAME)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = primaryButton
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
            ) {
                Text(text = "Empezar ahora", fontSize = 18.sp, color = Color.White)
            }


            // Botón modo difícil (comentado)
//            Button(
//                onClick = {},
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = Color(0xFFD9534F)
//                ),
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(55.dp)
//            ) {
//                Text(text = "Modo Difícil", fontSize = 18.sp, color = Color.White)
//            }


            // Botón tabla de puntuaciones
            Button(
                onClick = {
                    navController.navigate(Routes.SCOREBOARD)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = secondaryButton
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(text = "Tabla de puntuaciones", fontSize = 16.sp, color = Color.White)
            }
        }
    }
}


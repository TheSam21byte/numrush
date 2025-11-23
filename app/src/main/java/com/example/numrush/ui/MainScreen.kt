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
    // Colores definidos
    val background = Color(0xFF18222F)
    val primaryText = Color(0xFFEADFA1)
    val secondaryText = Color(0xFFBFC7D1)
    val primaryButton = Color(0xFFC9B560)
    val secondaryButton = Color(0xFF546A82)
    val hardModeButton = Color(0xFFD9534F)

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

            Text(
                text = "NumRush",
                fontSize = 42.sp,
                fontWeight = FontWeight.Bold,
                color = primaryText
            )

            Text(
                text = "¡Adivina el número secreto!",
                fontSize = 18.sp,
                color = secondaryText
            )

            Spacer(modifier = Modifier.height(40.dp))

            Image(
                painter = painterResource(id = R.mipmap.ic_launcher_foreground),
                contentDescription = "App Icon",
                modifier = Modifier
                    .size(160.dp)
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Botón: Empezar ahora (Modo Estándar)
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
                Text(text = "Empezar ahora (1-100)", fontSize = 18.sp, color = Color.White)
            }


            /* Botón: Modo Difícil (Descomentado y adaptado)
            Button(
                onClick = {
                    // ASUMIDO: Si tienes Routes.HARD_GAME definido, úsalo aquí.
                    // Si no, navega al modo estándar temporalmente.
                    // navController.navigate(Routes.HARD_GAME)
                    navController.navigate(Routes.GAME) // Usado como placeholder
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = hardModeButton // Color rojo distintivo
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
            ) {
                Text(text = "Modo Difícil (1-1000)", fontSize = 18.sp, color = Color.White)
            }
                */

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
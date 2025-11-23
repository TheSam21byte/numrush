package com.example.numrush.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.numrush.ui.viewmodel.GameViewModel

@Composable
fun GameScreen(viewModel: GameViewModel = viewModel(),
               onNavigateBack: () -> Unit) {

    val background = Color(0xFF18222F)
    val primaryText = Color(0xFFEADFA1)
    val secondaryText = Color(0xFFBFC7D1)
    val primaryButton = Color(0xFFC9B560)

    var userInput by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
            .padding(24.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        IconButton(
            onClick = { onNavigateBack()},
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 8.dp)
        ){
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Volver",
                tint = primaryText
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Adivina el Número",
                color = primaryText,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = viewModel.message.value,
                color = secondaryText,
                fontSize = 18.sp
            )

            OutlinedTextField(
                value = userInput,
                onValueChange = { newValue ->
                    userInput = newValue.filter { it.isDigit() } },
                label = { Text("Tu número") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Button(
                onClick = {
                    viewModel.onGuess(userInput)
                    userInput = ""
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                enabled = !viewModel.isGameOver.value,
                colors = ButtonDefaults.buttonColors(containerColor = primaryButton)
            ) {
                Text("Verificar", color = Color.White)
            }

            Button(
                onClick = {
                    viewModel.resetGame()
                    userInput = ""
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF546A82))
            ) {
                Text("Reiniciar", color = Color.White)
            }

            Text(
                text = "Intentos: ${viewModel.attempts.value}",
                color = secondaryText,
                fontSize = 16.sp
            )
        }
    }
}
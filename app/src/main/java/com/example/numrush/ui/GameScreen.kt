package com.example.numrush.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.numrush.ui.viewmodel.Guess
import androidx.compose.runtime.collectAsState


@Composable
fun GameScreen(
    viewModel: GameViewModel = viewModel(),
    onNavigateBack: () -> Unit
) {

    // Colores para la interfaz
    val background = Color(0xFF18222F)
    val primaryText = Color(0xFFEADFA1)
    val secondaryText = Color(0xFFBFC7D1)
    val primaryButton = Color(0xFFC9B560)
    val secondaryButton = Color(0xFF546A82)
    val cardColor = Color(0xFF283441) // Color para las tarjetas del historial
    val surrenderButton = Color(0xFFD32F2F) // Nuevo color para rendirse (Rojo)
    var userInput by remember { mutableStateOf("") }
    var playerNameInput by remember { mutableStateOf("") }
    val currentMessage by viewModel.message.collectAsState()
    val attempts by viewModel.attempts.collectAsState()
    val isGameOver by viewModel.isGameOver.collectAsState()
    val pastGuesses by viewModel.pastGuesses.collectAsState()
    val showNameInputDialog by viewModel.showNameInputDialog.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
            .padding(24.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        // Botón de Volver
        IconButton(
            onClick = { onNavigateBack() },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Volver",
                tint = primaryText
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Adivina el Número",
                color = primaryText,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(20.dp))

            // MENSAJE Y CONTADOR DE INTENTOS
            Card(
                colors = CardDefaults.cardColors(containerColor = cardColor),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = currentMessage,
                        color = secondaryText,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // CAMPO DE ENTRADA
            OutlinedTextField(
                value = userInput,
                onValueChange = { newValue ->
                    userInput = newValue.filter { it.isDigit() }
                },
                label = { Text("Tu número") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                enabled = !isGameOver,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = primaryText,
                    unfocusedTextColor = secondaryText,
                    focusedBorderColor = primaryButton,
                    unfocusedBorderColor = secondaryButton,
                    cursorColor = primaryText,
                    focusedLabelColor = primaryButton
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // BOTÓN VERIFICAR
            Button(
                onClick = {
                    viewModel.onGuess(userInput)
                    if (!isGameOver) {
                        userInput = ""
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                enabled = !isGameOver && userInput.isNotEmpty(),
                colors = ButtonDefaults.buttonColors(containerColor = primaryButton)
            ) {
                Text("Verificar", color = Color.White, fontSize = 18.sp)
            }

            if (attempts >= 8) {
                Spacer(modifier = Modifier.height(10.dp))
                // BOTON ME RINDO
                Button(
                    onClick = {
                        viewModel.surrenderGame()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    enabled = !isGameOver,
                    colors = ButtonDefaults.buttonColors(containerColor = surrenderButton)
                ) {
                    Text("Me Rindo", color = Color.White, fontSize = 16.sp)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // BOTÓN REINICIAR
            Button(
                onClick = {
                    viewModel.resetGame()
                    userInput = ""
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = secondaryButton)
            ) {
                Text("Reiniciar Juego", color = Color.White, fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(20.dp))
            Divider(color = secondaryButton.copy(alpha = 0.5f), thickness = 1.dp)
            Spacer(modifier = Modifier.height(10.dp))

            // SECCIÓN DE HISTORIAL DE INTENTOS
            if (pastGuesses.isNotEmpty()) {
                Text(
                    text = "Historial de Intentos - $attempts",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = primaryText,
                    modifier = Modifier.padding(bottom = 10.dp)
                )

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    reverseLayout = true,
                    verticalArrangement = Arrangement.Top
                ) {
                    itemsIndexed(
                        items = pastGuesses,
                        key = { index, _ -> index }
                    ) { _, guess ->
                        GuessItem(guess = guess, secondaryText = secondaryText, cardColor = cardColor)
                    }
                }
            } else {
                Text(
                    text = "Aún no hay intentos.",
                    fontSize = 16.sp,
                    color = secondaryText.copy(alpha = 0.7f),
                    modifier = Modifier.padding(top = 10.dp)
                )
            }
        }
    }

    if (showNameInputDialog) {
        AlertDialog(
            onDismissRequest = {
                viewModel.saveScoreWithName("Anónimo")
                playerNameInput = ""
            },
            title = {
                Text("¡Felicidades!", color = primaryText)
            },
            text = {
                Column {
                    Text("¡Has ganado! Ingresa tu nombre para registrar tu puntuación.", color = secondaryText)
                    Spacer(Modifier.height(16.dp))
                    OutlinedTextField(
                        value = playerNameInput,
                        onValueChange = { playerNameInput = it },
                        label = { Text("Tu nombre") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = primaryText,
                            unfocusedTextColor = secondaryText,
                            focusedBorderColor = primaryButton,
                            unfocusedBorderColor = secondaryButton,
                            cursorColor = primaryText,
                            focusedLabelColor = primaryButton
                        )
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.saveScoreWithName(playerNameInput)
                        playerNameInput = ""
                    },
                    enabled = playerNameInput.isNotBlank(),
                    colors = ButtonDefaults.buttonColors(containerColor = primaryButton)
                ) {
                    Text("Guardar Puntuación", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        viewModel.saveScoreWithName("Anónimo")
                        playerNameInput = ""
                    }
                ) {
                    Text("Omitir", color = secondaryText)
                }
            },
            containerColor = cardColor
        )
    }
}

@Composable
fun GuessItem(guess: Guess, secondaryText: Color, cardColor: Color) {
    Card(
        colors = CardDefaults.cardColors(containerColor = cardColor),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Intento: ${guess.number}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = secondaryText
            )
            Text(
                text = guess.result,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = when {
                    guess.result.contains("¡Correcto!") -> Color(0xFF4CAF50)
                    guess.result.contains("⬆️") -> Color(0xFFF0AD4E)
                    else -> Color(0xFF5BC0DE)
                }
            )
        }
    }
}
package com.example.numrush.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.numrush.data.Score
import com.example.numrush.ui.viewmodel.ScoreViewModel

@Composable
fun ScoreScreen(
    onNavigateBack: () -> Unit,
    viewModel: ScoreViewModel = viewModel() // Inyectamos el ViewModel de lectura
) {
    val background = Color(0xFF18222F)
    val primaryText = Color(0xFFEADFA1)
    val scores by viewModel.topScores.collectAsState(initial = emptyList())

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
            .padding(24.dp)
    ) {
        // Botón Volver
        IconButton(
            onClick = onNavigateBack,
            modifier = Modifier.align(Alignment.TopStart)
        ) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = primaryText)
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            Icon(Icons.Default.EmojiEvents, contentDescription = null, tint = primaryText, modifier = Modifier.size(64.dp))
            Text("Mejores Puntuaciones", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = primaryText)

            Spacer(modifier = Modifier.height(20.dp))

            if(scores.isEmpty()) {
                Text("Aún no hay records guardados.", color = Color.Gray)
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    // itemsIndexed nos da el elemento y su posición (0, 1, 2...)
                    itemsIndexed(scores) { index, score ->
                        ScoreItem(rank = index + 1, score = score)
                    }
                }
            }
        }
    }
}

@Composable
fun ScoreItem(rank: Int, score: Score) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2B3A4E)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Posición y Nombre del Jugador
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    "#$rank",
                    color = Color(0xFFEADFA1),
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = score.name,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp
                )
            }
            Spacer(modifier = Modifier.width(16.dp))

            // Intentos y Fecha
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    "${score.attempts} Intentos",
                    color = Color(0xFFC9B560),
                    fontWeight = FontWeight.Bold
                )
                Text(score.getFormattedDate(), color = Color.Gray, fontSize = 12.sp)
            }
        }
    }
}
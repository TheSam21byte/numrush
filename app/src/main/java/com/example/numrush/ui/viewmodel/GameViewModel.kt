package com.example.numrush.ui.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.numrush.data.AppDatabase
import com.example.numrush.data.Score
import com.example.numrush.domain.GameLogic
import kotlinx.coroutines.launch

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private val game = GameLogic()
    private val db = AppDatabase.getDatabase(application)
    private val scoreDao = db.scoreDao()
    var message = mutableStateOf("Ingresa un número entre 1 y 100")
        private set

    var attempts = mutableStateOf(0)
        private set

    var isGameOver = mutableStateOf(false)
        private set

    fun onGuess(input: String) {
        if (isGameOver.value || input.isBlank()) return

        val guess = input.toInt()
        when (val result = game.guess(guess)) {

            is GameLogic.GuessResult.Lower -> {
                message.value = "Más alto"
            }
            is GameLogic.GuessResult.Higher -> {
                message.value = "Más bajo"
            }
            is GameLogic.GuessResult.Correct -> {
                message.value = "¡Correcto! Lo lograste en ${result.attempts} intentos."
                isGameOver.value = true
                saveScore(result.attempts)
            }
        }

        attempts.value = guessAttempts()
    }
    private fun saveScore(attempts: Int) {
        viewModelScope.launch {
            val newScore = Score(attempts = attempts)
            scoreDao.insertScore(newScore)
        }
    }

    fun resetGame() {
        game.reset()
        message.value = "Ingresa un número entre 1 y 100"
        attempts.value = 0
        isGameOver.value = false
    }

    private fun guessAttempts() = attempts.value + 1
}
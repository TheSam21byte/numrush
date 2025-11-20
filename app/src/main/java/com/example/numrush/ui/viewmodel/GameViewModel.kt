package com.example.numrush.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.numrush.domain.GameLogic

class GameViewModel : ViewModel() {

    private val game = GameLogic()

    var message = mutableStateOf("Ingresa un número entre 1 y 100")
        private set

    var attempts = mutableStateOf(0)
        private set

    fun onGuess(input: String) {
        if (input.isBlank()) return

        val guess = input.toInt()
        when (val result = game.guess(guess)) {

            is GameLogic.GuessResult.Lower -> {
                message.value = "Más alto 🔼"
            }
            is GameLogic.GuessResult.Higher -> {
                message.value = "Más bajo 🔽"
            }
            is GameLogic.GuessResult.Correct -> {
                message.value = "¡Correcto! Lo lograste en ${result.attempts} intentos."
            }
        }

        attempts.value = guessAttempts()
    }

    fun resetGame() {
        game.reset()
        message.value = "Ingresa un número entre 1 y 100"
        attempts.value = 0
    }

    private fun guessAttempts() = attempts.value + 1
}
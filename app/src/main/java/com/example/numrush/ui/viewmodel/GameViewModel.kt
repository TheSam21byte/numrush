package com.example.numrush.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.numrush.data.AppDatabase
import com.example.numrush.data.Score
import com.example.numrush.domain.GameLogic
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class Guess(
    val number: Int,
    val result: String
)

private const val MAX_GUESSES = 7

class GameViewModel(application: Application) : AndroidViewModel(application) {
    private val game = GameLogic()
    private val db = AppDatabase.getDatabase(application)
    private val scoreDao = db.scoreDao()


    private val _message = MutableStateFlow("Ingresa un número entre 1 y 100")
    val message: StateFlow<String> = _message.asStateFlow()

    private val _attempts = MutableStateFlow(0)
    val attempts: StateFlow<Int> = _attempts.asStateFlow()

    private val _isGameOver = MutableStateFlow(false)
    val isGameOver: StateFlow<Boolean> = _isGameOver.asStateFlow()

    private val _pastGuesses = MutableStateFlow<List<Guess>>(emptyList())
    val pastGuesses: StateFlow<List<Guess>> = _pastGuesses.asStateFlow()

    private val _showNameInputDialog = MutableStateFlow(false)
    val showNameInputDialog: StateFlow<Boolean> = _showNameInputDialog.asStateFlow()

    private val _finalAttempts = MutableStateFlow(0)


    /**
     * Procesa un intento del usuario.
     * @param input El número ingresado como String.
     */

    fun onGuess(input: String) {
        if (_isGameOver.value || input.isBlank()) return

        val guess = input.toIntOrNull()
        if (guess == null) {
            _message.value = "Por favor, ingresa un número válido."
            return
        }

        if (guess !in 1..100){
            _message.value = "El número debe estar entre 1 y 100"
            return
        }

        _attempts.value += 1

        when (val result = game.guess(guess)) {
            is GameLogic.GuessResult.Lower -> {
                _message.value = "Más alto ⬆️"
                addGuessToHistory(guess, "Más alto ⬆️")
            }
            is GameLogic.GuessResult.Higher -> {
                _message.value = "Más bajo ⬇️"
                addGuessToHistory(guess, "Más bajo ⬇️")
            }
            is GameLogic.GuessResult.Correct -> {
                _message.value = "¡Correcto! Lo lograste en ${_attempts.value} intentos."
                _isGameOver.value = true
                _finalAttempts.value = _attempts.value
                _showNameInputDialog.value = true

                addGuessToHistory(guess, "¡Correcto! 🎉")
            }
        }
    }

    fun surrenderGame() {
        if (_isGameOver.value) return
        _isGameOver.value = true
        _message.value = "Te rendiste. El número secreto era: ${game.getSecretNumber()}"
    }

    fun saveScoreWithName(name: String) {
        val playerName = name.trim().takeIf { it.isNotEmpty() } ?: "Anónimo"

        viewModelScope.launch {
            val attempts = _finalAttempts.value

            // Lógica de guardado limpia: La entidad Score ya tiene el campo 'name'.
            val newScore = Score(name = playerName, attempts = attempts)
            scoreDao.insertScore(newScore)
        }
        _showNameInputDialog.value = false
    }

    fun hideNameInputDialog() {
        _showNameInputDialog.value = false
    }

    private fun addGuessToHistory(number: Int, result: String) {
        val newGuess = Guess(number, result)
        _pastGuesses.value = (_pastGuesses.value + newGuess).takeLast(MAX_GUESSES)
    }

    fun resetGame() {
        game.reset()
        _message.value = "Ingresa un número entre 1 y 100"
        _attempts.value = 0
        _isGameOver.value = false
        _pastGuesses.value = emptyList()
        _showNameInputDialog.value = false
        _finalAttempts.value = 0
    }
}
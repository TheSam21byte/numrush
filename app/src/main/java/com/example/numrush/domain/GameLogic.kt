package com.example.numrush.domain

import kotlin.random.Random

class GameLogic {

    private var secretNumber = Random.nextInt(1, 101)
    private var attempts = 0

    fun guess(number: Int): GuessResult {
        attempts++

        return when {
            number < secretNumber -> GuessResult.Lower
            number > secretNumber -> GuessResult.Higher
            else -> GuessResult.Correct(attempts)
        }
    }

    fun getCurrentAttempts(): Int {
        return attempts
    }

    fun reset(){
        secretNumber = Random.nextInt(1, 101)
        attempts = 0
    }

    sealed class GuessResult {
        object Lower : GuessResult()
        object Higher : GuessResult()
        data class Correct(val attempts: Int) : GuessResult()
    }

}
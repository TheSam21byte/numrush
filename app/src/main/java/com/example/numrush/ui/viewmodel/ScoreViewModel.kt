package com.example.numrush.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.numrush.data.AppDatabase
import com.example.numrush.data.Score
import kotlinx.coroutines.flow.Flow

class ScoreViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)
    private val scoreDao = db.scoreDao()
    val topScores: Flow<List<Score>> = scoreDao.getTopScores()
}
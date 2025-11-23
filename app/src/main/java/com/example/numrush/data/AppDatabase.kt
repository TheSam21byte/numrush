package com.example.numrush.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Definimos qué entidades (tablas) y qué versión tiene la base de datos
@Database(entities = [Score::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    // Conectamos el DAO
    abstract fun scoreDao(): ScoreDao

    // Patrón Singleton (Código estándar para Room)
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // Si ya existe, la devuelve. Si no, la crea.
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "numrush_database" // El nombre del archivo físico en el celular
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
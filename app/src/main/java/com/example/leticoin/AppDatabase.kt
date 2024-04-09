package com.example.leticoin

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.leticoin.accounts.Account
import com.example.leticoin.accounts.AccountsDao
import com.example.leticoin.achievements.Achievement
import com.example.leticoin.achievements.AchievementsDao

@Database(entities = [Account::class, Achievement::class], version = 1, exportSchema = false)
abstract class AppDatabase:RoomDatabase() {
    abstract fun accountsDao(): AccountsDao
    abstract fun achievementsDao(): AchievementsDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        private const val DB_NAME = "leti_coin.db"

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DB_NAME
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
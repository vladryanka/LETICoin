package com.example.leticoin

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.Room.databaseBuilder
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
        fun getInstance(application: Application?): AppDatabase {

            if (INSTANCE == null) {
                INSTANCE = databaseBuilder(
                    application!!,
                    AppDatabase::class.java,
                    DB_NAME
                ).build()
                Log.d("Doing","Мы создали БД")
                return INSTANCE as AppDatabase
            }
            else
                return INSTANCE as AppDatabase
        }
//        fun getInstance(context: Context): AppDatabase {
//                return INSTANCE ?: synchronized(this) {
//
//                    val instance = Room.databaseBuilder(
//                        context.applicationContext,
//                        AppDatabase::class.java,
//                        DB_NAME
//                    ).build()
//                    Log.d("Doing","Мы создали БД")
//                    INSTANCE = instance
//                    return instance
//            }
//        }
    }
}
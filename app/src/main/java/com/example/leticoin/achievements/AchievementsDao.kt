package com.example.leticoin.achievements

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AchievementsDao {
    @Query("SELECT * FROM achievements")
    fun getAchievements(): LiveData<List<Achievement>>

    @Insert
    fun add(achievement: Achievement)

    @Query("DELETE FROM achievements WHERE id = :id")
    fun remove(id: Int)

    @Query("SELECT * FROM achievements WHERE username LIKE :searchQuery")
    fun searchAchievement(searchQuery: String): Achievement?
}
package com.example.projectanmp.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AchievementDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAchievement(achievement: Achievement)

    @Query("DELETE FROM achievements")
    fun deleteAll()

    @Query("SELECT * FROM achievements WHERE game_id = :gameId")
    fun getAchievementsForGame(gameId: Int): List<Achievement>
}
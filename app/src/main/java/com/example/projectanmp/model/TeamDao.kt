package com.example.projectanmp.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TeamDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTeam(team: Team):Long

    @Query("SELECT * FROM team WHERE game_id = :gameId")
    fun getTeamsByGameId(gameId: Int): List<Team>

    @Query("DELETE FROM team")
    fun deleteAllTeams()
}

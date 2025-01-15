package com.example.projectanmp.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface MemberDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMember(member: Member): Long

    @Query("SELECT * FROM member WHERE team_id = :teamId")
    fun getMembersByTeamId(teamId: Int): List<Member>

    @Query("DELETE FROM member")
    fun deleteAllMembers()
}

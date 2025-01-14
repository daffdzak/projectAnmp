package com.example.projectanmp.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AppliesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProposal(applies: Apply)

    @Query("SELECT * FROM applies WHERE username = :username")
    fun getApplyByUsernameSync(username: String): List<Apply>

    @Query("SELECT * FROM applies WHERE username = :username")
    fun getApplyByUsername(username: String): LiveData<List<Apply>>
}
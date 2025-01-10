package com.example.projectanmp.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LoginDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg login: Login)

    @Query("SELECT * FROM Login")
    fun selectAllLogin(): List<Login>

    @Query("SELECT * FROM Login WHERE uuid= :id")
    fun selectLogin(id:Int): Login

    @Delete
    fun deleteLogin(login: Login)
}




package com.example.projectanmp.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg users: User)

    @Query("SELECT * FROM user ORDER BY username ASC")
    fun selectAllUsers(): List<User>

    @Query("UPDATE user SET firstName = :firstName, lastName = :lastName, username = :username, password = :password WHERE id = :id")
    fun update(
        id: Int,
        firstName: String,
        lastName: String,
        username: String,
        password: String
    ): Int

    @Query("SELECT * FROM user WHERE id = :id")
    fun selectUser(id: Int): User?

    @Delete
    fun deleteUser(user: User)

    @Query("SELECT * FROM user WHERE username = :username")
    fun getUserByUsername(username: String): User?

    @Query("SELECT * FROM user WHERE username = :username")
    fun getUserByUsernameLive(username: String): LiveData<User>

//    @Query("UPDATE user SET like_count = like_count + 1 WHERE username = :username")
//    fun incrementLikeCount(username: String)
}





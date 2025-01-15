package com.example.projectanmp.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UpcomingEventDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUpcomingEvents(events: List<UpcomingEventEntity>)

    @Query("SELECT * FROM upcoming_events")
    fun getAllUpcomingEvents(): LiveData<List<UpcomingEventEntity>>

    @Query("DELETE FROM upcoming_events")
    fun deleteAllUpcomingEvents()
}

package com.example.projectanmp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class EsportGamesResponse(
    val esport_games: List<EsportGame>
)

data class EsportGame(
    val game_title: String,
    val description: String,
    val image: String,
    val achievements: List<Achievement>,
    val upcoming_events: List<UpcomingEvent>
)

data class Achievement(
    val event_name: String,
    val team: String,
    val year: Int

)

data class UpcomingEvent(
    val event_name: String,
    val year: Int,
    val month: String,
    val day: Int,
    val time: String,
    val game: String,
    val description: String,
    val event_image: String
)

data class Team(
    val name: String,
    val members: List<String>
)

@Entity
data class Login(
    @ColumnInfo(name="username")
    var usrname:String,
    @ColumnInfo(name="password")
    var passwrd:String
) {
    @PrimaryKey(autoGenerate = true)
    var uuid:Int =0
}


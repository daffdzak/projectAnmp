package com.example.projectanmp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class EsportGamesResponse(
    val esport_games: List<EsportGame>
)

data class EsportGame(
    @SerializedName("id")
    val id: Int,

    @SerializedName("image")
    val image: String,

    @SerializedName("game_title")
    val game_title: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("achievement")
    val achievement: List<AchievementJson>,

//    @SerializedName("upcoming_events")
//    val upcomingEvents: List<UpcomingEvent>
)

data class AchievementJson(
    @SerializedName("event_name")
    val event_name: String,

    @SerializedName("team")
    val team: String,

    @SerializedName("year")
    val year: Int

)

//data class UpcomingEvent(
//    val event_name: String,
//    val year: Int,
//    val month: String,
//    val day: Int,
//    val time: String,
//    val game: String,
//    val description: String,
//    val event_image: String
//)

//data class Team(
//    val name: String,
//    val members: List<String>
//)

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int =0,
    @ColumnInfo(name="firstName")
    var firstName:String,
    @ColumnInfo(name="lastName")
    var lastName:String,
    @ColumnInfo(name="username")
    var usrname:String,
    @ColumnInfo(name="password")
    var passwrd:String,
    @ColumnInfo(name = "like_count")
    var likeCount: Int = 0,
    @ColumnInfo(name = "description")
    var desc: String
)

@Entity(tableName = "game")
data class  Game(
    @PrimaryKey @ColumnInfo(name = "id")
    var id:Int,
    @ColumnInfo(name="game_title")
    var gameName:String,
    @ColumnInfo(name="image")
    var image:String,
    @ColumnInfo(name="description")
    var description:String
)

@Entity(tableName = "achievements")
data class Achievement(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "event_name")
    val eventName: String,

    @ColumnInfo(name = "team")
    val team: String,

    @ColumnInfo(name = "year")
    val year: Int,

    @ColumnInfo(name = "game_id")
    val competitionId: Int
)

@Entity(tableName = "applies")
data class Apply(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "game")
    var game: String,

    @ColumnInfo(name = "team")
    var team: String,

    @ColumnInfo(name = "description")
    var description: String,

    @ColumnInfo(name = "status")
    var status: String = "WAITING"
)






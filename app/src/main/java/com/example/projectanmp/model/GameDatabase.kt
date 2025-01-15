package com.example.projectanmp.model

import android.content.Context
import android.provider.CalendarContract.Instances
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException


@Database(entities = [User::class, Game::class, Achievement::class, Apply::class, Team::class, Member::class], version = 3)
abstract class GameDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun gameDao(): GameDao
    abstract fun appliesDao():AppliesDao
    abstract fun achievementDao(): AchievementDao
    abstract fun teamDao(): TeamDao
    abstract fun memberDao():MemberDao

    companion object{
        @Volatile private var instance:GameDatabase? = null

        fun buildDatabase(context: Context, scope: CoroutineScope): GameDatabase {
            return instance ?: synchronized(this) {
                val instance1 = Room.databaseBuilder(
                    context.applicationContext,
                    GameDatabase::class.java,
                    "game_db"
                ).addCallback(DatabaseCallback(scope, context))
                    .fallbackToDestructiveMigration()
                    .build()
                instance = instance1
                instance1
            }
        }

        private class DatabaseCallback(
            private val scope: CoroutineScope,
            private val context: Context
        ) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                instance?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database, context)
                    }
                }
            }
        }

        suspend fun populateDatabase(database: GameDatabase, context: Context) {
            val jsonString = loadJsonFromAsset("data.json", context)
            if (jsonString == null) {
                Log.e("GameDatabase", "Failed to load JSON from assets.")
                return
            }

            val gson = Gson()
            val typeToken = object : TypeToken<EsportGamesResponse>() {}.type
            val esportWrapper: EsportGamesResponse? = gson.fromJson(jsonString, typeToken)

            if (esportWrapper == null || esportWrapper.esport_games.isEmpty()) {
                Log.e("GameDatabase", "No games found in JSON.")
                return
            }

            Log.d("GameDatabase", "Number of games to insert: ${esportWrapper.esport_games.size}")

            esportWrapper.esport_games.forEach { esport ->
                try {
                    // Insert game
                    val game = Game(
                        id = esport.id,
                        image = esport.image,
                        gameName = esport.game_title,
                        description = esport.description
                    )
                    database.gameDao().insertGame(game)
                    Log.d("GameDatabase", "Inserted game: ${esport.game_title}")

                    esport.achievements.forEach { achievement ->
                        val newAchievement = Achievement(
                            eventName = achievement.event_name,
                            team = achievement.team,
                            year = achievement.year,
                            competitionId = game.id
                        )
                        database.achievementDao().insertAchievement(newAchievement)
                        Log.d("GameDatabase", "Inserted achievement: ${achievement.event_name}")
                    }

                    val hardcodedTeams = when (esport.id) {
                        1 -> listOf(
                            Team(teamName = "T1", gameId = esport.id),
                            Team(teamName = "JDG", gameId = esport.id)
                        )
                        2 -> listOf(
                            Team(teamName = "OG", gameId = esport.id),
                            Team(teamName = "LDG", gameId = esport.id)
                        )
                        3 -> listOf(
                            Team(teamName = "NAVI", gameId = esport.id),
                            Team(teamName = "Team Spirit", gameId = esport.id)
                        )
                        4 -> listOf(
                            Team(teamName = "PRX", gameId = esport.id),
                            Team(teamName = "Sentinel", gameId = esport.id)
                        )
                        5 -> listOf(
                            Team(teamName = "Team Iota", gameId = esport.id),
                            Team(teamName = "Team Kappa", gameId = esport.id)
                        )
                        else -> emptyList()
                    }
                    hardcodedTeams.forEach { team ->

                        val teamId = database.teamDao().insertTeam(team)
                        Log.d("GameDatabase", "Inserted team: ${team.teamName} with ID: $teamId")

                        val members = listOf(
                            Member(name = "${esport.game_title} Akira (${team.teamName})", teamId = teamId.toInt()),
                            Member(name = "${esport.game_title} Hiroshi (${team.teamName})", teamId = teamId.toInt()),
                            Member(name = "${esport.game_title} Sakura (${team.teamName})", teamId = teamId.toInt())
                        )
                        members.forEach { member ->
                            database.memberDao().insertMember(member)

                            Log.d("GameDatabase", "Inserted member: ${member.name} for team ID: ${member.teamId}")
                        }
                    }
                    } catch (e: Exception) {
                    Log.e("GameDatabase", "Error inserting game/achievement: ${esport.game_title}", e)
                }
            }
        }

        private fun loadJsonFromAsset(filename: String, context: Context): String? {
            return try {
                context.assets.open(filename).bufferedReader().use { it.readText() }
            } catch (ex: IOException) {
                ex.printStackTrace()
                null
            }
        }
    }
}
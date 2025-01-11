package com.example.projectanmp.model

import android.content.Context
import android.provider.CalendarContract.Instances
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


@Database(entities = [User::class, Game::class, Achievement::class], version = 1)
abstract class GameDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun gameDao(): GameDao
    abstract fun achievementDao(): AchievementDao

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
            val gson = Gson()
            val typeToken = object : TypeToken<EsportGamesResponse>() {}.type
            val competitionsWrapper: EsportGamesResponse = gson.fromJson(jsonString, typeToken)

            competitionsWrapper.esport_games.forEach { comp ->
                val games = Game(
                    id = comp.id,
                    image = comp.image,
                    gameName = comp.game_title,
                    description = comp.description
                )
                database.gameDao().insertGame(games)

                comp.achievement.forEach { achievement ->
                    val newAchievement = Achievement(
                        eventName = achievement.event_name,
                        team = achievement.team,
                        year = achievement.year,
                        competitionId = comp.id
                    )
                    database.achievementDao().insertAchievement(newAchievement)
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
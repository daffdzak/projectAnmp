//package com.example.projectanmp.model
//
//import android.content.Context
//import androidx.room.Database
//import androidx.room.Room
//import androidx.room.RoomDatabase
//
//@Database(entities = [Login::class], version = 1)
//abstract class LoginDatabase : RoomDatabase() {
//    abstract fun loginDao(): LoginDao
//    abstract fun gameDao(): GameDao
//    abstract fun achievementDao(): AchievementDao
//
//    companion object {
//        @Volatile
//        private var instance: LoginDatabase? = null
//        private val LOCK = Any()
//
//
//        fun getInstance(context: Context): LoginDatabase {
//            return instance ?: synchronized(LOCK) {
//                instance ?: buildDatabase(context).also { instance = it }
//            }
//        }
//
//
//        private fun buildDatabase(context: Context): LoginDatabase {
//            return Room.databaseBuilder(
//                context.applicationContext,
//                LoginDatabase::class.java,
//                "newLoginDB"
//            ).build()
//        }
//    }
//}

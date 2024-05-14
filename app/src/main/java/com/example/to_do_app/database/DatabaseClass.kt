package com.example.to_do_app.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [To_Do_Data::class] , version = 1)
abstract class DatabaseClass : RoomDatabase() {
    abstract fun toDoDao(): To_do_Dao

    companion object {
        @Volatile
        private var INSTANCE: DatabaseClass? = null
        fun getDatabase(context: Context): DatabaseClass {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext ,
                    DatabaseClass::class.java ,
                    "App_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
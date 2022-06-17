package com.example.taskmanagement.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.taskmanagement.data.models.Task



@Database(entities = [Task::class], version = 1, exportSchema = false)
    abstract class RoomDb : RoomDatabase() {
        abstract fun taskDao(): TaskDao

        companion object {
            private const val DB_NAME = "task_db"
            private var dbInstance: RoomDb? = null
            fun getDatabase(context: Context): RoomDb? {
                if (dbInstance == null) {
                    synchronized(RoomDb::class.java) {
                        if (dbInstance == null) {
                            dbInstance = Room.databaseBuilder(context.applicationContext
                                , RoomDb::class.java, DB_NAME)
                                .fallbackToDestructiveMigration().
                                build()
                        }
                    }
                }
                return dbInstance
            }
        }
    }
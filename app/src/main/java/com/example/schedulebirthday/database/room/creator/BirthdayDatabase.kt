package com.example.schedulebirthday.database.room.creator

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.schedulebirthday.database.room.dao.UserEventDao
import com.example.schedulebirthday.database.room.entity.UserEventEntity
import com.example.schedulebirthday.database.room.repository.RoomUserEventRepository

@Database(
    entities = [UserEventEntity::class],
    version = 4
)
abstract class BirthdayDatabase: RoomDatabase() {
    abstract fun userDao(): UserEventDao

    companion object {
        @Volatile
        private var INSTANCE: BirthdayDatabase? = null

        fun getDatabaseBirthday(context: Context): BirthdayDatabase {
            if (INSTANCE != null) return INSTANCE!!

            synchronized(this) {
                INSTANCE = Room
                    .databaseBuilder(context, BirthdayDatabase::class.java, "birthday_db")
                    .fallbackToDestructiveMigration()
                    .build()

                return INSTANCE!!
            }
        }

        fun initAllTableDB(applicationContext: Context) {
            RoomUserEventRepository.profileDatabase =
                RoomUserEventRepository.initializeDB(context = applicationContext)
        }

        /*fun destroyDataBase() {
            INSTANCE = null
        }*/
    }
}
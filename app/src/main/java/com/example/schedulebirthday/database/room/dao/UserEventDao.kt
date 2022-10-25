package com.example.schedulebirthday.database.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.schedulebirthday.database.room.entity.UserEventEntity

@Dao
interface UserEventDao {

    @Insert
    fun saveUserProfile(user: UserEventEntity)

    @Query("SELECT * FROM user_entity")
    fun getUserProfile(): List<UserEventEntity>

    @Query("DELETE FROM user_entity WHERE id = :id")
    fun deleteUserProfile(id: String)
}
package com.example.shedulebirthday.database.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shedulebirthday.database.room.entity.UserEventEntity
import com.example.shedulebirthday.model.UserFullModel

@Dao
interface UserEventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveUserProfile(user: UserEventEntity)

    @Query("SELECT * FROM user_entity")
    fun getUserProfile(): List<UserEventEntity>

    @Query("DELETE FROM user_entity WHERE id = :id")
    fun deleteUserProfile(id: String)
}
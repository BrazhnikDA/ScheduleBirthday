package com.example.shedulebirthday.database.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shedulebirthday.model.UserFullModel

@Dao
interface UsersDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveUserProfile(user: UserFullModel)

    @Query("SELECT * FROM user_entity")
    fun getUserProfile(): List<UserFullModel>

    @Query("DELETE FROM user_entity WHERE id = :id")
    fun deleteUserProfile(id: String)
}
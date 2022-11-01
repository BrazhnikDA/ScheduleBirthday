package com.example.schedulebirthday.database.repository

import com.example.schedulebirthday.database.room.entity.UserEventEntity
import com.example.schedulebirthday.model.UserModel

interface UserEventRepository {
    suspend fun saveUserProfile(user: UserEventEntity)
    suspend fun getUserProfile(): List<UserModel>
    suspend fun deleteUserProfile(id: String)
}
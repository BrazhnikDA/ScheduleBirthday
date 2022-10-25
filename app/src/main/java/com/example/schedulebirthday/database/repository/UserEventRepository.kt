package com.example.schedulebirthday.database.repository

import com.example.schedulebirthday.database.room.entity.UserEventEntity
import com.example.schedulebirthday.model.UserFullModel

interface UserEventRepository {
    suspend fun saveUserProfile(user: UserEventEntity)
    suspend fun getUserProfile(): List<UserFullModel>
    suspend fun deleteUserProfile(id: String)
}
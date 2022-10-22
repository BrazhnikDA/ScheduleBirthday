package com.example.shedulebirthday.database.repository

import com.example.shedulebirthday.model.UserFullModel

interface UserEventRepository {
    suspend fun saveUserProfile(user: UserFullModel)
    suspend fun getUserProfile(): List<UserFullModel>
    suspend fun deleteUserProfile(id: String)
}
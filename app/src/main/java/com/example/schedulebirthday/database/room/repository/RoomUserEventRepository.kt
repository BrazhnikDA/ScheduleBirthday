package com.example.schedulebirthday.database.room.repository

import android.content.Context
import com.example.schedulebirthday.database.repository.UserEventRepository
import com.example.schedulebirthday.database.room.creator.BirthdayDatabase
import com.example.schedulebirthday.database.room.entity.UserEventEntity
import com.example.schedulebirthday.model.UserModel

class RoomUserEventRepository {
    companion object: UserEventRepository {

        var profileDatabase: BirthdayDatabase? = null

        fun initializeDB(context: Context): BirthdayDatabase {
            return BirthdayDatabase.getDatabaseBirthday(context)
        }

        override suspend fun saveUserProfile(user: UserEventEntity) {
            profileDatabase!!.userDao().saveUserProfile(user)
        }

        override suspend fun getUserProfile(): List<UserModel> {
            return UserModel.convertListEntityToListModel(profileDatabase!!.userDao().getUserProfile())
        }

        override suspend fun deleteUserProfile(id: String) {
            profileDatabase!!.userDao().deleteUserProfile(id)
        }

    }
}
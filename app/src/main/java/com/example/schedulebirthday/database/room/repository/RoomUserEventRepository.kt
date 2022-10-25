package com.example.schedulebirthday.database.room.repository

import android.content.Context
import com.example.schedulebirthday.database.repository.UserEventRepository
import com.example.schedulebirthday.database.room.creator.BirthdayDatabase
import com.example.schedulebirthday.model.UserFullModel

class RoomUserEventRepository {
    companion object: UserEventRepository {

        var profileDatabase: BirthdayDatabase? = null

        fun initializeDB(context: Context): BirthdayDatabase {
            return BirthdayDatabase.getDatabaseBirthday(context)
        }

        override suspend fun saveUserProfile(user: UserFullModel) {
            profileDatabase!!.userDao().saveUserProfile(UserFullModel.convertListModelToEntity(user))
        }

        override suspend fun getUserProfile(): List<UserFullModel> {
            return UserFullModel.convertListEntityToListModel(profileDatabase!!.userDao().getUserProfile())
        }

        override suspend fun deleteUserProfile(id: String) {
            profileDatabase!!.userDao().deleteUserProfile(id)
        }

    }
}
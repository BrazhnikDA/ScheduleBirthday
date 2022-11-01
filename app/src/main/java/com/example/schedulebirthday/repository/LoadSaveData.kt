package com.example.schedulebirthday.repository

import androidx.lifecycle.MutableLiveData
import com.example.schedulebirthday.database.room.entity.UserEventEntity
import com.example.schedulebirthday.database.room.repository.RoomUserEventRepository
import com.example.schedulebirthday.model.UserModel

class LoadSaveData(private val users: MutableLiveData<List<UserModel>>) {

    suspend fun getSaveUsers() {
        users.postValue(RoomUserEventRepository.getUserProfile())
    }

    suspend fun setSaveUser(user: UserEventEntity) {
         RoomUserEventRepository.saveUserProfile(user)
    }
}
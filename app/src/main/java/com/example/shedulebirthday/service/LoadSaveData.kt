package com.example.shedulebirthday.service

import androidx.lifecycle.MutableLiveData
import com.example.shedulebirthday.database.room.repository.RoomUserEventRepository
import com.example.shedulebirthday.model.UserFullModel

class LoadSaveData(private val users: MutableLiveData<List<UserFullModel>>) {

    suspend fun getSaveUsers() {
        users.postValue(RoomUserEventRepository.getUserProfile())
    }

    suspend fun setSaveUser(user: UserFullModel) {
         RoomUserEventRepository.saveUserProfile(user)
    }
}
package com.example.shedulebirthday.service

import com.example.shedulebirthday.model.UserFullModel

class LoadSaveData {

    fun getSaveData(): ArrayList<UserFullModel> {
        val list = ArrayList<UserFullModel>()
        list.add(UserFullModel(0, "Dima", null, "9", "11", null, null))
        list.add(UserFullModel(1, "Dima2", null, "9", "11", null, null))
        list.add(UserFullModel(2, "Dima3", null, "9", "11", null, null))
        list.add(UserFullModel(3, "Dima4", null, "9", "11", null, null))
        list.add(UserFullModel(4, "Dima5", null, "9", "11", null, null))
        list.add(UserFullModel(5, "Dima6", null, "9", "11", null, null))
        list.add(UserFullModel(6, "Dima7", null, "9", "11", null, null))
        list.add(UserFullModel(7, "Dima8", null, "9", "11", null, null))
        list.add(UserFullModel(8, "Dima9", null, "9", "11", null, null))
        list.add(UserFullModel(9, "Dima10", null, "9", "11", null, null))
        list.add(UserFullModel(10, "Dima11", null, "9", "11", null, null))
        return list
    }

    fun setSaveData() {

    }
}
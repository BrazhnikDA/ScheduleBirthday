package com.example.schedulebirthday.repository

import com.example.schedulebirthday.model.UserModel

enum class StatusSort {
    ALPHABETICALLY,
    DATE_UP,
    DATE_DOWN;

    companion object {
        fun sortList(status: StatusSort, list: List<UserModel>): List<UserModel> {
            return when (status) {
                ALPHABETICALLY -> {
                    list.sortedBy { it.name }
                }
                DATE_UP -> {
                    list.sortedByDescending { it.period }
                }
                DATE_DOWN -> {
                    list.sortedBy { it.period }
                }
            }
        }
    }
}
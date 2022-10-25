package com.example.schedulebirthday.model

import com.example.schedulebirthday.database.room.entity.UserEventEntity

data class UserFullModel(
    val id: Int,
    val name: String,
    val picture: String,
    val day: String,
    val months: String,
    val year: String,
    val age: String
) {
    fun calculateAge(): Int {
        //TODO На основе года рождения вернуть возраст пользователя
        return 0
    }

    fun calculateDateOfBorn(): Int {
        //TODO На основе возраста вернуть год рождения пользователя
        return 0
    }

    override fun toString(): String {
        return "UserModel(name='$name', day='$day', Months='$months', Year=$year, Age=$age)"
    }

    companion object {
        fun convertEntityToModel(entity: UserEventEntity): UserFullModel {
            return UserFullModel(
                entity.id!!.toInt(), entity.name, entity.picture, entity.day, entity.months,
                entity.year, entity.age
            )
        }

        fun convertListEntityToListModel(entity: List<UserEventEntity>): List<UserFullModel> {
            var res: List<UserFullModel> = mutableListOf()
            for (item in entity) {

                res = res + mutableListOf(convertEntityToModel(item))
            }
            return res
        }

        fun convertListModelToEntity(user: UserFullModel): UserEventEntity {
            return UserEventEntity(
                user.name,
                user.picture,
                user.day,
                user.months,
                user.year,
                user.age
            )
        }
    }
}
package com.example.schedulebirthday.model

import com.example.schedulebirthday.database.room.entity.UserEventEntity
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*

data class UserModel(
    val id: Int,
    val name: String,
    val picture: String,
    val day: String,
    val months: String,
    val year: String,
    val age: String,
    val period: Long?
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
        private fun convertEntityToModel(entity: UserEventEntity): UserModel {
            return UserModel(
                entity.id!!.toInt(),
                entity.name,
                entity.picture,
                entity.day,
                entity.months,
                entity.year,
                entity.age,
                calculateRemainingDaysToBirthday(days = entity.day, months = entity.months)
            )
        }

        fun convertListEntityToListModel(entity: List<UserEventEntity>): List<UserModel> {
            var res: List<UserModel> = mutableListOf()
            for (item in entity) {
                res = res + mutableListOf(convertEntityToModel(item))
            }
            return res
        }

        fun convertListModelToEntity(user: UserModel): UserEventEntity {
            return UserEventEntity(
                user.name,
                user.picture,
                user.day,
                user.months,
                user.year,
                user.age
            )
        }

        fun calculateRemainingDaysToBirthday(days: String, months: String): Long {
            val sdf = SimpleDateFormat("yyyy")
            val currentDate = sdf.format(Date())
            val dateString = days + months + currentDate
            var from = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("ddMMyyyy"))
            val today = LocalDate.now()
            var period = ChronoUnit.DAYS.between(today, from)

            return if (period < 0) {
                from = from.plusYears(1)
                //period = ChronoUnit.DAYS.between(today, from)
                //period
                return ChronoUnit.DAYS.between(today, from)
            } else {
                period
            }
        }
    }
}
package com.example.shedulebirthday.model

data class UserFullModel(
    val id: Int,
    val name: String,
    val picture: String?,
    val day: String,
    val Months: String,
    val Year: String?,
    val Age: String?
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
        return "UserModel(id=$id, name='$name', day='$day', Months='$Months', Year=$Year, Age=$Age)"
    }
}
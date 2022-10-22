package com.example.shedulebirthday.model

data class UserShortModel (
    val id: Long,
    val name: String,
    val picture: String?,
) {
    override fun toString(): String {
        return "UserShortModel(id=$id, name='$name', picture=$picture)"
    }

}
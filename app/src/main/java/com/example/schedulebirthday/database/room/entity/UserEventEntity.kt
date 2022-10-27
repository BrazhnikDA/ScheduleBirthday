package com.example.schedulebirthday.database.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_entity")
data class UserEventEntity(
    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "picture")
    val picture: String,

    @ColumnInfo(name = "day")
    val day: String,

    @ColumnInfo(name = "months")
    val months: String,

    @ColumnInfo(name = "year")
    val year: String,

    @ColumnInfo(name = "age")
    val age: String
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null
}
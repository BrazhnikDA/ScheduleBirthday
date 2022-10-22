package com.example.shedulebirthday.database.room.entity

import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Blob

@Entity(tableName = "user_entity")
class UserEventEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Long,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "picture")
    val picture: String,

    @ColumnInfo(name = "day")
    val day: String,

    @ColumnInfo(name = "months")
    val months: String,

    @ColumnInfo(name = "year")
    val year: String? = null,

    @ColumnInfo(name = "age")
    val age: String? = null
)
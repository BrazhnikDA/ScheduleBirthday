package com.example.schedulebirthday.repository

import android.provider.Settings.Global.getString
import com.example.schedulebirthday.R

// Format Date
// symbol(example): . / - _
// Input: 07.11.2003
// Output: 07112005
fun convertStringEditTextToStringDate(date: String, symbol: Char): String {
    if (date.isEmpty()) return "null"

    val parseDate = date.split(symbol)
    return if (parseDate.size == 3) {
        val day = parseDate[0]
        val month = parseDate[1]
        val year = parseDate[2]
        day + month + year
    } else "Format: error"
}

fun convertStringEditTextToArrayStringDate(date: String, symbol: Char): List<String> {
    if (date.isEmpty()) return mutableListOf("null", "null", "null")
    val split = date.split(symbol)
    return if(split.size == 3)
        date.split(symbol)
    else mutableListOf("null", "null", "null")
}
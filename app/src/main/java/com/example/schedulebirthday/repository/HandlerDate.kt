package com.example.schedulebirthday.repository

import com.example.schedulebirthday.utilities.displayToast
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

fun parseLocalDateOrNull(text: String, formatter: DateTimeFormatter): Boolean {
    return try {
        LocalDate.parse(text, formatter)
        true
    } catch (ex: DateTimeParseException) {
        //context?.displayToast("Дата неверна!")
        false
    }
}

fun calculateYear(day: String, months: String, year: String): Int {
    val dateString = day + months + year
    val from = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("ddMMyyyy"))
    val today = LocalDate.now()
    return Period.between(from, today).years
}


fun calculateYear(date: String): Int {
    val from = LocalDate.parse(date, DateTimeFormatter.ofPattern("ddMMyyyy"))
    val today = LocalDate.now()
    return Period.between(from, today).years
}
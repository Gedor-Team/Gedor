package com.gedorteam.gedor.util

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

fun formatDateString(input: String): String {
    val inputFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
    inputFormatter.timeZone = TimeZone.getTimeZone("UTC")

    val date = inputFormatter.parse(input)

    val outputFormatter = SimpleDateFormat("EEEE, d MMMM yyyy", Locale.ENGLISH)
    return outputFormatter.format(date)
}
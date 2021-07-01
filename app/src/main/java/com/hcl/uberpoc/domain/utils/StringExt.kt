package com.hcl.uberpoc.domain.utils

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date

fun String.toLowerCaseDefault(): String = this.toLowerCase(Locale.getDefault())

fun String.toAlphaNumeric(): String = this.replace("[^\\s\\p{L}\\p{N}]".toRegex(), "")

// Default input date sample - 2021-06-25T06:25:00.000
// Default output date sample - 25.06.2021 6:25
fun String.toFormattedDate(
    inputFormat: String = "yyyy-MM-dd'T'HH:mm:ss",
    outputFormat: String = "dd.MM.yyyy HH:mm",
    locale: Locale = Locale.getDefault(),
): String {
    if (this.isBlank()) return this

    val parser = SimpleDateFormat(inputFormat, locale)
    val formatter = SimpleDateFormat(outputFormat, locale)
    return formatter.format(parser.parse(this))
}

fun String.toDate(
    format: String,
    locale: Locale = Locale.getDefault(),
): Date = SimpleDateFormat(format, locale).parse(this)
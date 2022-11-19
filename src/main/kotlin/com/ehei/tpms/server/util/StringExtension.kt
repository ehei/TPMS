package com.ehei.tpms.server.util

import java.time.LocalDateTime.of
import java.time.LocalDateTime.ofInstant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

/**
 * Convert to string
 *
 * @return
 */
fun Date.convertToString(): String {
    val localDate = ofInstant(this.toInstant(), ZoneOffset.UTC).toLocalDate()
    return localDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(Locale.US))
}

/**
 * Create date from
 *
 * @param year
 * @param monthOfYear
 * @param dayOfMonth
 * @return
 */
fun createDateFrom(year: Int, monthOfYear: Int, dayOfMonth: Int): Date {
    val localDateTime = of(year, monthOfYear, dayOfMonth, 0, 0, 0, 0)

    return Date.from(localDateTime.toInstant(ZoneOffset.UTC))
}

/**
 * Create values from
 *
 * @return
 */
fun Date.createValuesFrom(): List<Int> {
    val localDate = ofInstant(this.toInstant(), ZoneOffset.UTC).toLocalDate()

    return listOf(localDate.year, localDate.monthValue, localDate.dayOfMonth)
}
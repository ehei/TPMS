package com.ehei.tpms.server.util

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*


class StringExtensionTest {

    @Test
    internal fun `Date can be generated from selected values`() {
        val createdDate: Date = createDateFrom(2022, 8, 12)
        val localDateTime = LocalDateTime.of(2022, 8, 12, 0, 0, 0, 0)

        Assertions.assertThat(createdDate).isEqualToIgnoringHours(Date.from(localDateTime.toInstant(ZoneOffset.UTC)))
    }

    @Test
    internal fun `Date generated zeroes out seconds and milliseconds`() {
        val createdDate: Date = createDateFrom(2022, 9, 12)

        val localDate = LocalDateTime.ofInstant(createdDate.toInstant(), ZoneOffset.UTC).toLocalDate()
        val formattedDate = localDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(Locale.US))

        Assertions.assertThat(formattedDate).isEqualTo("Sep 12, 2022")
    }

    @Test
    internal fun `Date convertToString uses Medium format`() {
        val createdDate: Date = createDateFrom(2022, 7, 20)

        Assertions.assertThat(createdDate.convertToString()).isEqualTo("Jul 20, 2022")
    }

    @Test
    internal fun `Date createValuesFrom extracts the year, month, and day values`() {
        val createdDate: Date = createDateFrom(2013, 12, 8)

        val createValuesFrom = createdDate.createValuesFrom()

        Assertions.assertThat(createValuesFrom).containsExactly(2013, 12, 8)
    }
}
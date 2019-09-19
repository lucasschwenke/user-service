package br.com.mymoney.user.domain.utils

import org.joda.time.DateTime
import org.joda.time.LocalDate
import org.joda.time.LocalTime
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

val MIDDAY = LocalTime(12, 0, 0, 0)

fun DateTime.toJavaLocalDateTime() = Instant.ofEpochMilli(toInstant().millis)
    .atZone(ZoneId.of(zone.id)).toLocalDateTime()!!

fun LocalDateTime.toJodaDateTime() = LocalDate(year, monthValue, dayOfMonth).toDateTime(MIDDAY)!!
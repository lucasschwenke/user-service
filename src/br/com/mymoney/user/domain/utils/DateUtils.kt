package br.com.mymoney.user.domain.utils

import org.joda.time.DateTime
import java.time.Instant
import java.time.ZoneId

fun DateTime.toJavaLocalDateTime() = Instant.ofEpochMilli(toInstant().millis)
    .atZone(ZoneId.of(zone.id))?.toLocalDateTime()

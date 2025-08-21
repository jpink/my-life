package my.life.time

import kotlinx.datetime.LocalTime
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

val LocalTime.inWholeMinutes get() = hour * 60 + minute

fun LocalTime.duration(to: LocalTime) = (to.inWholeMinutes - inWholeMinutes).minutes

operator fun LocalTime.plus(duration: Duration) =
    (hour * 60 + minute + duration.inWholeMinutes.toInt()).let { LocalTime((it / 60) % 24, it % 60) }

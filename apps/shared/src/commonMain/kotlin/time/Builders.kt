package my.life.time

import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

val Int.dayOfWeek get() = DayOfWeek.of(this)
val Int.hour get() = ClockTime(this)
fun Int.hour(minute: Int) = ClockTime(this, minute)

val kotlin.time.Duration.wrap get() = Duration(this)
val Int.hours get() = this.hours.wrap
val Int.minutes get() = this.minutes.wrap

val String.time get() = Time.of(this)

fun date(year: Int = Year.EPOCH, month: Int = 1, day: Int = 1) = Date(year, month, day)

operator fun Time.div(duration: Duration) = StartAndDuration(this, duration)
operator fun Time.div(end: Time) = StartAndEnd(this, end)


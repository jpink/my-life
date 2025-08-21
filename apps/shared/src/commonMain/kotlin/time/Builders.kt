package my.life.time

import kotlin.time.Duration

val Int.dayOfWeek get() = MyDayOfWeek(this)
val Int.hour get() = MyTime(this)
fun Int.hour(minute: Int) = MyTime(this, minute)

val String.time get() = Time.parse(this)

fun date(year: Int = MyYear.EPOCH, month: Int = 1, day: Int = 1) = MyDate(year, month, day)

operator fun Time.div(duration: Duration) = StartAndDuration(this, duration)
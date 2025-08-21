package my.life.routine.dsl

import kotlinx.datetime.DayOfWeek
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes
import my.life.routine.dsl.DefaultGroup.*
import my.life.routine.dsl.DefaultPriority.*
import my.life.routine.dsl.DefaultRoutine.*
import my.life.time.hour

enum class DefaultRoutine {
    // Meals
    BREAKFAST,
    LUNCH,
    SNACK,
    DINNER,
    SUPPER
}

enum class DefaultGroup {
    MORNING
}

enum class DefaultPriority {
    VITAL,
    HEALTH,
    EVERYDAY,
    ECONOMIC;

    val value get() = ordinal + 1
}

val defaults get() = configure {

    // Meals https://www.is.fi/hyvaolo/art-2000010110615.html
    priority(VITAL)
    daily(BREAKFAST, 7.hour, 20.minutes)
    daily(LUNCH, 11.hour, 30.minutes)
    daily(SNACK, 14.hour(30), 10.minutes)
    daily(DINNER, 17.hour, 45.minutes)
    daily(SUPPER, 20.hour, 20.minutes)

    work(8.hour, 8.hours, 25.minutes,
        DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY)

    group(MORNING, BREAKFAST)

    /*
        val defaultMorningRoutine = 1.hours + 30.minutes
        val defaultDayOffActivity = defaultWork.start + 1.hours
     */
}
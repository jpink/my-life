package my.life.routine

import kotlinx.datetime.DayOfWeek
import my.life.routine.DefaultGroup.*
import my.life.routine.DefaultPriority.*
import my.life.routine.DefaultRoutine.*
import my.life.time.hour
import my.life.time.hours
import my.life.time.minutes

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

fun buildDefaults() = configure {

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
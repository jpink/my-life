package my.life.routine

import my.life.routine.Routine.RepeatBase.Daily
import my.life.time.Day

class WeeklyPlan(val config: Config) {
    var planned = false
    private val dayPlans = Day.entries.associateWith { DailyPlan(config, it) }

    fun plan(): WeeklyPlan {
        if (!planned) {
            config.routines.filter { it.base == Daily }.forEach { routine ->
                routine.days.forEach { get(it) + routine }
            }
            planned = true
        }
        return this
    }

    operator fun get(day: Day) = dayPlans.getValue(day)
}
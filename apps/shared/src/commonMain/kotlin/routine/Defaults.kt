package my.life.routine

import my.life.common.Country
import my.life.routine.TimeVariable.*
import my.life.time.Date
import my.life.time.DayOfWeek
import my.life.time.hour
import my.life.time.hours
import my.life.time.minutes

// Default priorities
const val VITAL = "vital"
const val HEALTH = "health"
const val WELFARE = "welfare"
const val EVERYDAY = "everyday"
const val ECONOMIC = "economic"
const val FREE_TIME = "freeTime"

// Default groups
const val AWAKENING = "awakening"
const val ACTIVITY = "activity"
const val BEDTIME = "bedtime"

// Referenced routines
const val BREAKFAST = "breakfast"

fun configure(location: String, birth: Date, params: Params.() -> Unit, block: Config.() -> Unit) =
    Config(Params(Country(location), birth).apply(params)).apply {
        // Meals https://www.is.fi/hyvaolo/art-2000010110615.html
        daily(BREAKFAST, VITAL, AWAKENING) {
            //start = FixedTime(7.hour)
            duration = 20.minutes
            endFormula = ActivityStart
        }
        daily("lunch", VITAL, ACTIVITY, 11.hour, 30.minutes)
        daily("snack", WELFARE, ACTIVITY, 14.hour(30), 10.minutes)
        daily("meals.tomorrow", EVERYDAY)..
                daily("dinner", VITAL, start = 17.hour, duration = 45.minutes)
        daily("supper", WELFARE, BEDTIME, 20.hour, 20.minutes)

        daily("dishwasher.empty", EVERYDAY, null, 7.hour, 10.minutes)
        if (man) daily("shaved", WELFARE, AWAKENING)
        daily("teeth.awakening", WELFARE, AWAKENING, 8.hour, 4.minutes)
        daily("teethGaps", HEALTH, BEDTIME, 21.hour, 5.minutes)..
                daily("teeth.bedtime", HEALTH, BEDTIME) { duration = 3.minutes }
        daily("cleaning", EVERYDAY) {
            // https://www.is.fi/asuminen/art-2000010094660.html
            duration = 15.minutes
        }
        daily("mails.retrieved", EVERYDAY) {
            // Days: Monday, Wednesday, Friday
            duration = 5.minutes
        }
        daily("mails.read", EVERYDAY) {
            // Days: Monday, Wednesday, Friday
            duration = 15.minutes
        }
        daily("electricity", ECONOMIC, null, 14.hour, 2.minutes)
        daily("dishwasher.timer", ECONOMIC, null, 20.hour(30), 10.minutes)
        weekly("trash", EVERYDAY)
        if (townHouse) {
            weekly("lawn", EVERYDAY) {
                duration = 2.hours
            }
        }

        work(
            8.hour, 8.hours, 25.minutes,
            *DayOfWeek.entries.take(5).toTypedArray()
        )

        daily("sleep", VITAL) {
            duration = when { // https://www.kaypahoito.fi/nix02713
                age < 14 -> 10.hours
                age < 18 -> 9.hours
                age < 65 -> 8.hours
                else -> 7.hours(30)
            }
        }

        group("morning", BREAKFAST) // 1.hours(30)

        defaultsSet()
    }.apply(block)
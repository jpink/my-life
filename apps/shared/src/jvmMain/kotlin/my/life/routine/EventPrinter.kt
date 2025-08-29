package my.life.routine

import fi.life.my.life.time.ClockTimeFormatter
import my.life.time.ClockTime
import my.life.time.DayOfWeek
import my.life.time.Time
import java.util.ResourceBundle

class EventPrinter(val config: Config) {
    val locale = config.params.locale
    val bundle = ResourceBundle.getBundle("Routines", locale)!!
    val formatter = ClockTimeFormatter(locale)

    fun format(time: Time) = formatter.format(time as ClockTime)

    val String.local get() = if (bundle.containsKey(this)) bundle.getString(this) else this

    fun printPlan(day: DayOfWeek) = config.plan(day).joinToString("\n") { it.item }

    val Event.item get() = "${format(time)} ${if (end) '>' else '<'} ${id.local}"
}
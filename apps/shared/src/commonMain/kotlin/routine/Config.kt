package my.life.routine

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import my.life.time.DayOfWeek
import my.life.time.Duration
import my.life.time.Interval
import my.life.time.Time
import my.life.time.div
import my.life.routine.Routine.RepeatBase
import my.life.routine.Routine.RepeatBase.*

/** Routine configuration. */
@Serializable
class Config(val params: Params) {
    private val working = mutableMapOf<DayOfWeek, Interval>()
    private val routines = mutableListOf<Routine>()
    private val groups = mutableMapOf<String, List<String>>()

    @Transient
    private var default = true

    val age get() = params.birth.age(params.timeZone)
    val man get() = params.man == true
    val woman get() = params.man == false
    val townHouse get() = params.townHouse

    fun daily(id: String, priority: String, group: String? = null, init: Routine.() -> Unit = {}) =
        routine(id, Daily, priority, group, init)
    fun daily(id: String, priority: String, group: String? = null, start: Time, duration: Duration, init: Routine.() -> Unit = {}) =
        daily(id, priority, group) {
            this.startFormula = FixedTime(start)
            this.duration = duration
        init()
    }

    fun weekly(id: String, priority: String, group: String? = null, init: Routine.() -> Unit = {}) =
        routine(id, Weekly, priority, group, init)

    fun monthly(id: String, priority: String, init: Routine.() -> Unit = {}) =
        routine(id, Monthly, priority, init = init)

    fun yearly(id: String, priority: String, init: Routine.() -> Unit = {}) =
        routine(id, Yearly, priority, init = init)

    private fun routine(id: String, base: RepeatBase, priority: String, group: String? = null, init: Routine.() -> Unit = {}) =
        Routine(id, base, priority, default, group).apply {
            init(this)
            routines.add(this)
        }

    private fun disable(id: String) {}

    fun group(group: String, vararg routines: String) {
        groups[group] = routines.toMutableList()
    }

    fun work(start: Time, workingHours: Duration, travelTime: Duration? = null, vararg days: DayOfWeek) {
        val interval = start / workingHours
        days.forEach { working[it] = interval }
    }

    fun plan(day: DayOfWeek) = routines.filter { it.base == Daily }.flatMap { it.plan(day, this) }.sorted()

    fun defaultsSet() { default = false }
}
package my.life.routine

import kotlinx.datetime.DayOfWeek
import kotlinx.serialization.Serializable
import kotlinx.datetime.LocalTime
import kotlinx.serialization.Transient
import my.life.time.Duration
import my.life.time.Interval
import my.life.time.Time
import my.life.time.div

@Serializable
class RoutineDefinition(val priority: Int) {
    @Transient
    var id: String? = null
    var start: LocalTime? = null
    var duration: Duration? = null
    var interval: Interval? = null
}

class Event {
    var id: String? = null
}

@Serializable
class Configuration {
    private var priority = 0
    private val working = mutableMapOf<DayOfWeek, Interval>()
    private val routines = mutableMapOf<String, RoutineDefinition>()
    private val groups = mutableMapOf<String, List<String>>()

    private fun addRoutine(id: String, init: (RoutineDefinition.() -> Unit)?) = RoutineDefinition(priority).apply {
        init?.invoke(this)
        this.id = id
        routines[id] = this
    }

    fun priority(priority: DefaultPriority) {
        this.priority = priority.value
    }

    fun daily(routine: DefaultRoutine, start: Time, duration: Duration, init: (RoutineDefinition.() -> Unit)? = null)
            = addRoutine(routine.toString()) { interval = start / duration }

    fun group(group: DefaultGroup, vararg routines: DefaultRoutine) {
        groups[group.toString()] = routines.map { it.toString() }
    }

    fun work(start: Time, workingHours: Duration, travelTime: Duration? = null, vararg days: DayOfWeek) {
        val interval = start / workingHours
        days.forEach { working[it] = interval }
    }
}

fun configure(init: Configuration.() -> Unit) = Configuration().apply(init)

package my.life.routine

import kotlinx.serialization.Serializable
import my.life.time.DayOfWeek
import my.life.time.Duration

@Serializable
data class Routine(
    /** Routine ID which may be DefaultRoutine.name, Definition of Done or link. */
    val id: String,
    val base: RepeatBase,
    /** Priority ID. Capitalized means default. */
    val priority: String,
    val default: Boolean = false,
    val group: String? = null
) {
    enum class RepeatBase { Daily, Weekly, Monthly, Yearly }
    var after: String? = null
    /** Prefer after definition except defaults. */
    var before: String? = null
    var startFormula: TimeFormula? = null
    var duration: Duration? = null
    var endFormula: TimeFormula? = null
    //var interval: Interval? = null

    operator fun rangeTo(other: Routine): Routine {
        before = other.id
        other.after = id
        return other
    }

    fun plan(day: DayOfWeek, config: Config): List<Event> {
        val events = mutableListOf<Event>()
        var start = startFormula?.count(config, day)
        var end = endFormula?.count(config, day)
        duration?.let {
            if (start != null) end = start + it
            if (end != null) start = end - it
        }
        start?.let { events.add(Event(it, id)) }
        end?.let { events.add(Event(it, id, true)) }
        return events
    }
}
package my.life.routine

import my.life.time.Time

data class Event(val time: Time, val id: String, val end: Boolean = false) : Comparable<Event> {
    override fun compareTo(other: Event) = time.compareTo(other.time)
    override fun toString() = "$time ${if (end) '>' else '<'} $id}"
}
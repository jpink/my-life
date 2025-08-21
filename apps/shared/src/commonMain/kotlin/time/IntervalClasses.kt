package my.life.time

import kotlin.time.Duration

data class StartAndDuration(override val start: Time, override val duration: Duration) : Interval {
    override val end: Time get() = TODO("Not yet implemented")
    override fun toString() = startAndEnd
}
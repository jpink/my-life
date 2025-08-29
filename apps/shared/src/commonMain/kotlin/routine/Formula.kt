package my.life.routine

import kotlinx.serialization.Serializable
import my.life.time.DayOfWeek
import my.life.time.Time
import my.life.time.hour
import kotlin.jvm.JvmInline

@Serializable
sealed interface TimeFormula {
    fun count(config: Config, day: DayOfWeek): Time
}

@JvmInline
value class FixedTime(val time: Time): TimeFormula {
    override fun count(config: Config, day: DayOfWeek) = time
}

enum class TimeVariable : TimeFormula {
    ActivityStart {
        override fun count(config: Config, day: DayOfWeek) = 0.hour // TODO
    }
}
package my.life.time

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlin.text.toInt
import kotlin.jvm.JvmInline

interface AbsoluteTime : Time

@JvmInline
value class Date(val value: LocalDate) : AbsoluteTime {
    constructor(input: CharSequence) : this(LocalDate.parse(input))
    constructor(year: Int = Year.EPOCH, month: Int = 1, day: Int = 1) : this(LocalDate(year, month, day))
    override fun toString() = value.toString()
}

@JvmInline
value class DateTime(val value: LocalDateTime) : AbsoluteTime {
    constructor(input: CharSequence) : this(LocalDateTime.parse(input))
    constructor(year: Int = Year.EPOCH, monthNumber: Int = 1, dayOfMonth: Int = 1, hour: Int = 0, minute: Int = 0) :
            this(LocalDateTime(year, monthNumber, dayOfMonth, hour, minute))
    constructor(date: LocalDate, time: LocalTime = LocalTime(0, 0)) : this(LocalDateTime(date, time))
    override fun toString() = value.toString()
}

@JvmInline
value class DayOfYear(val value: Int = 1) : AbsoluteTime {
    constructor(input: String) : this(input.removePrefix(PREFIX).toInt())
    override fun toString() = format(value)
    companion object {
        const val PREFIX = "-"
        fun format(number: Int) = (PREFIX + number).padStart(3, '0')
        fun match (input: String) = input.startsWith(PREFIX)
    }
}

@JvmInline
value class Year(val value: Int = EPOCH) : AbsoluteTime {
    constructor(input: String) : this(input.toInt())
    override fun toString() = value.toString()
    companion object {
        const val EPOCH = 1970
    }
}

data class YearAndDay(val year: Year = Year(), val day: DayOfYear = DayOfYear()) : AbsoluteTime {
    constructor(input: String) : this(Year(input.take(4)), DayOfYear(input.substring(4)))
    override fun toString() = format(year, day)
    companion object {
        fun format(year: Int, dayOfYear: Int) = "$year-${dayOfYear.pad}"
        fun format(year: Year, dayOfYear: DayOfYear) = "$year$dayOfYear"
    }
}

data class YearAndMonth(val year: Year = Year(), val month: Month = Month.January) : AbsoluteTime {
    constructor(input: String) : this(Year(input.take(4)), Month.of(input.substring(5)))
    override fun toString() = format(year, month)
    companion object {
        fun format(year: Year, month: my.life.time.Month) = "$year-${month.number.pad}"
    }
}

data class YearAndWeek(val year: Year = Year(), val week: Week = Week()) : AbsoluteTime {
    constructor(input: String) : this(Year(input.take(4)), Week(input.substring(4)))
    override fun toString() = format(year, week)
    companion object {
        fun format(year: Int, week: Int) = "$year${Week.format(week)}"
        fun format(year: Year, week: Week) = "$year$week"
        fun match(input: String) = input.substring(4, 6) == "-W"
    }
}

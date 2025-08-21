package my.life.time

import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.Month
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.number
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import my.life.time.Time.Form
import kotlin.text.toInt
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.minutes
import kotlin.jvm.JvmInline

val Int.zeroFill get() = toString().padStart(2, '0')
val Int.zeroesFill get() = toString().padStart(3, '0')

@JvmInline
value class MyDate(val value: LocalDate) : Time {
    override val form get() = Form.DATE
    constructor(input: CharSequence) : this(LocalDate.parse(input))
    constructor(year: Int = MyYear.EPOCH, month: Int = 1, day: Int = 1) :
            this(LocalDate(year, month, day))
    override fun toString() = value.toString()
}

@JvmInline
value class MyDateAndTime(val value: LocalDateTime) : Time {
    override val accuracy get() = 1.minutes
    override val length get() = LENGTH
    constructor(input: CharSequence) : this(LocalDateTime.parse(input))
    constructor(year: Int = MyYear.EPOCH, monthNumber: Int = 1, dayOfMonth: Int = 1, hour: Int = 0, minute: Int = 0) :
            this(LocalDateTime(year, monthNumber, dayOfMonth, hour, minute))
    // TODO ctor
    constructor(date: LocalDate, time: LocalTime = LocalTime(0, 0)) : this(LocalDateTime(date, time))
    //constructor(date: MyDate = MyDate(), time: MyTime = MyTime()) : this(date.value, time.value)
    override fun toString() = value.toString()
    companion object {
        const val LENGTH = 10 + 1 + MyTime.LENGTH
    }
}

@JvmInline
value class MyDayOfMonth(val value: Int = 1) : Time {
    override val accuracy get() = 1.days
    override val length get() = LENGTH
    constructor(input: String) : this(input.removePrefix(PREFIX).toInt())
    override fun toString() = format(value)
    companion object {
        const val PREFIX = "---"
        const val LENGTH = PREFIX.length + 2
        fun format(number: Int) = PREFIX + number.zeroFill
        fun match(input: String) = input.startsWith(PREFIX)
    }
}

@JvmInline
value class MyDayOfWeek(val value: DayOfWeek = DayOfWeek.MONDAY) : Time {
    override val form get() = Form.DAY_OF_WEEK
    val number get() = value.isoDayNumber
    constructor(input: String) : this(input.removePrefix(PREFIX).toInt())
    constructor(number: Int) : this(parse(number))
    override fun toString() = format1(number)
    companion object {
        const val PREFIX = "-W-"
        fun format1(number: Int) = number.toString()
        fun format4(number: Int) = PREFIX + number
        fun match (input: String) = input.startsWith(PREFIX)
        fun parse(number: Int) = DayOfWeek.entries[number - 1]
    }
}

@JvmInline
value class MyDayOfYear(val value: Int = 1) : Time {
    override val accuracy get() = 1.days
    override val length get() = LENGTH
    constructor(input: String) : this(input.removePrefix(PREFIX).toInt())
    override fun toString() = format(value)
    companion object {
        const val LENGTH = 4
        const val PREFIX = "-"
        fun format(number: Int) = PREFIX + number.zeroesFill
        fun match (input: String) = input.startsWith(PREFIX)
    }
}

@JvmInline
value class MyMonth(val value: Month = Month.JANUARY) : Time {
    override val accuracy get() = DAYS.days
    override val length get() = LENGTH
    val number get() = value.number
    constructor(input: String) : this(input.removePrefix(PREFIX).toInt())
    constructor(number: Int) : this(parse(number))
    override fun toString() = format(value)
    companion object {
        const val PREFIX = "--"
        const val DAYS = MyYear.DAYS / 12
        const val LENGTH = 2
        fun format(month: Month) = format(month.number)
        fun format(number: Int) = PREFIX + number.zeroFill
        fun match(input: String) = input.startsWith(PREFIX)
        // Month.of implemented only in JVM.
        fun parse(number: Int) = when (number) {
            1 -> Month.JANUARY
            2 -> Month.FEBRUARY
            3 -> Month.MARCH
            4 -> Month.APRIL
            5 -> Month.MAY
            6 -> Month.JUNE
            7 -> Month.JULY
            8 -> Month.AUGUST
            9 -> Month.SEPTEMBER
            10 -> Month.OCTOBER
            11 -> Month.NOVEMBER
            12 -> Month.DECEMBER
            else -> throw IllegalArgumentException("Invalid ISO month: $this")
        }
    }
}

data class MyMonthAndDay(val month: MyMonth = MyMonth(), val day: MyDayOfMonth = MyDayOfMonth()) : Time {
    override val accuracy get() = 1.days
    override val length get() = LENGTH
    constructor(input: String) : this(
        MyMonth(input.take(4)),
        MyDayOfMonth(input.substring(5)))
    override fun toString() = format(month, day)
    companion object {
        const val PREFIX = "--"
        const val LENGTH = MyMonth.LENGTH + 3
        fun format(month: MyMonth, day: MyDayOfMonth) = "$month-${day.value.zeroFill}"
        fun match(input: String) = input.startsWith(PREFIX)
    }
}

@JvmInline
value class MyTime(val value: LocalTime) : Time {
    override val accuracy get() = 1.minutes
    override val length get() = LENGTH
    constructor(input: CharSequence) : this(LocalTime.parse(input))
    constructor(hour: Int = 0, minute: Int = 0) : this(LocalTime(hour, minute))
    override fun toString() = value.toString()
    companion object {
        const val LENGTH = 5
    }
}

@JvmInline
value class MyWeek(val value: Int = 1) : Time {
    override val accuracy get() = 7.days
    override val length get() = LENGTH
    constructor(input: String) : this(input.removePrefix(PREFIX).toInt())
    override fun toString() = format(value)
    companion object {
        const val PREFIX = "-W"
        const val LENGTH = PREFIX.length + 2
        fun format(value: Int) = PREFIX + value.zeroFill
        fun match(input: String) = input.startsWith(PREFIX)
    }
}

data class MyWeekAndDay(val week: MyWeek = MyWeek(), val day: MyDayOfWeek = MyDayOfWeek()) : Time {
    override val accuracy get() = 1.days
    override val length get() = LENGTH
    constructor(input: String) : this(
        MyWeek(input.take(4)),
        MyDayOfWeek(input.substring(5)))
    override fun toString() = format(week, day)
    companion object {
        const val LENGTH = MyWeek.LENGTH + 1 + 1
        fun format(week: MyWeek, day: MyDayOfWeek) = "$week-${day.number}"
    }
}

@JvmInline
value class MyYear(val value: Int = EPOCH) : Time {
    override val accuracy get() = DAYS.days
    override val length get() = LENGTH
    constructor(input: String) : this(input.toInt())
    override fun toString() = value.toString()
    companion object {
        const val DAYS = 365.2425
        const val EPOCH = 1970
        const val LENGTH = 4
        const val MILLENNIUM = 2000
    }
}

data class MyYearAndMonth(val year: MyYear = MyYear(), val month: MyMonth = MyMonth()) : Time {
    override val accuracy get() = MyMonth.DAYS.days
    override val length get() = LENGTH
    constructor(input: String) : this(
        MyYear(input.take(4)),
        MyMonth(input.substring(5)))
    override fun toString() = format(year, month)
    companion object {
        const val LENGTH = MyYear.LENGTH + 1 + MyMonth.LENGTH
        fun format(year: MyYear, month: MyMonth) = "$year-${month.number.zeroFill}"
    }
}

data class MyYearAndWeek(val year: MyYear = MyYear(), val week: MyWeek = MyWeek()) : Time {
    override val accuracy get() = 7.days
    override val length get() = LENGTH
    constructor(input: String) : this(
        MyYear(input.take(4)),
        MyWeek(input.substring(4)))
    //constructor(year: Int, week: Int = 1) : this(MyYear(year), MyWeek(week))
    override fun toString() = format(year, week)
    companion object {
        const val LENGTH = MyYear.LENGTH + MyWeek.LENGTH
        fun format(year: Int, week: Int) = "$year${MyWeek.format(week)}"
        fun format(year: MyYear, week: MyWeek) = "$year$week"
        fun match(input: String) = input.substring(4, 6) == "-W"
    }
}

data class MyYearWeekAndDay(
    val year: MyYear = MyYear(),
    val week: MyWeek = MyWeek(),
    val day: MyDayOfWeek = MyDayOfWeek()
) : Time {
    override val accuracy get() = 1.days
    override val length get() = LENGTH
    constructor(input: String) : this(
        MyYear(input.take(4)),
        MyWeek(input.substring(4, 8)),
        MyDayOfWeek(input.substring(9))
    )
    constructor(year: Int, week: Int = 1, dayOfWeek: Int = 1) : this(
        MyYear(year),
        MyWeek(week),
        MyDayOfWeek(dayOfWeek))
    override fun toString() = format(year, week, day)
    companion object {
        const val LENGTH = MyYearAndWeek.LENGTH + 2
        fun format(year: Int, week: Int, dayOfWeek: Int) = "$year${MyWeek.format(week)}-$dayOfWeek"
        fun format(year: MyYear, week: MyWeek, dayOfWeek: MyDayOfWeek) = "$year$week-${dayOfWeek.number}"
    }
}

data class MyYearAndDay(
    val year: MyYear = MyYear(),
    val day: MyDayOfYear = MyDayOfYear()
) : Time {
    override val accuracy get() = 1.days
    override val length get() = LENGTH
    constructor(input: String) : this(
        MyYear(input.take(4)),
        MyDayOfYear(input.substring(4))
    )
    //constructor(year: Int, dayOfYear: Int = 1) : this(MyYear(year), MyDayOfYear(dayOfYear))
    override fun toString() = format(year, day)
    companion object {
        const val LENGTH = MyYearAndWeek.LENGTH + 2
        fun format(year: Int, dayOfYear: Int) = "$year-${dayOfYear.zeroesFill}"
        fun format(year: MyYear, dayOfYear: MyDayOfYear) = "$year$dayOfYear"
    }
}
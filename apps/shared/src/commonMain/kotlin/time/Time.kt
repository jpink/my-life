package my.life.time

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days

/** Local time, datetime, date, year-week-day, day of week, week, year-week, day of month, month, month of year or year. */
@Serializable(with = Time.Serializer::class)
sealed interface Time {
    enum class Form(val unit: Unit, vararg val lengths: Int) {
        DATE(Unit.DAY, /* TODO 6, 8,*/ 10),
        DAY_OF_WEEK(Unit.DAY, 1, 4)
    }
    object Serializer : KSerializer<Time> {
        override val descriptor = PrimitiveSerialDescriptor("Time", PrimitiveKind.STRING)
        override fun serialize(encoder: Encoder, value: Time) = encoder.encodeString(value.toString())
        override fun deserialize(decoder: Decoder) = parse(decoder.decodeString())
    }
    enum class Unit(val duration: Duration) {
        DAY(1.days)
    }
    val form : Form get() = TODO()
    @Deprecated("Form is used instead!")
    val accuracy: Duration get() = TODO()
    @Deprecated("Form is used instead!")
    val length: Int get() = TODO()
    companion object {
        fun parse(input: String) = when (input.length) {
            1 -> MyDayOfWeek(input)                                     // 1    known   TODO Prefer
            2 -> MyMonth(input)                                         // 01   known   TODO Prefer
            3 -> MyWeek(input)                                          // W01  known   TODO Prefer
            4 -> when {
                MyDayOfWeek.match(input) -> MyDayOfWeek(input)          // -W-1, deprecated TODO postpone
                MyMonth.match(input) -> MyMonth(input)                  // --01, deprecated TODO postpone
                MyWeek.match(input) -> MyWeek(input)                    // -W01, unknown    TODO postpone
                MyDayOfYear.match(input) -> MyDayOfYear(input)          // -001, deprecated
                else -> MyYear(input)                                   // 1970, standard
            }
            5 ->
                if (MyDayOfMonth.match(input)) MyDayOfMonth(input)      // ---01, deprecated
                else MyTime(input)                                      // 00:00, standard  TODO T-handling
            6 -> MyWeekAndDay(input)                                    // -W01-1, deprecated
            7 ->
                if (MyMonthAndDay.match(input)) MyMonthAndDay(input)    // --01-01, deprecated
                else MyYearAndMonth(input)                              // 1970-01, standard
            8 ->
                if (MyYearAndWeek.match(input)) MyYearAndWeek(input)    // 1970-W01, standard
                else MyYearAndDay(input)                                // 1970-001, standard
            10 ->
                if (input[5] == 'W') MyYearWeekAndDay(input)            // 1970-W01-1, standard
                else MyDate(input)                                      // 1970-01-01, standard
            16 -> MyDateAndTime(input)                                  // 1970-01-01T00:00, standard
            else -> throw IllegalArgumentException("Unable to parse time of '$input'!")
        }
    }
}

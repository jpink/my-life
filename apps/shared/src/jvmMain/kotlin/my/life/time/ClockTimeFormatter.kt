package fi.life.my.life.time

import kotlinx.datetime.toJavaLocalTime
import my.life.time.ClockTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

class ClockTimeFormatter(locale: Locale, style: FormatStyle = FormatStyle.SHORT) {
    val formatter = DateTimeFormatter.ofLocalizedTime(style).withLocale(locale)!!

    fun format(time: ClockTime) = formatter.format(time.value.toJavaLocalTime())!!
}
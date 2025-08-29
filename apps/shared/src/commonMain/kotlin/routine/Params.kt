package my.life.routine

import kotlinx.serialization.Serializable
import my.life.common.Country
import my.life.time.Date
import my.life.time.minutes

/** User defined parameters. */
@Serializable
class Params(
    var location: Country,
    val birth: Date
) {
    /** Is Housing type town house or block of flats. */
    var townHouse = false
    var man: Boolean? = null

    var nationality = location
    var language = location.defaultLanguage
    var priorities = listOf(VITAL, HEALTH, WELFARE, EVERYDAY, ECONOMIC, FREE_TIME)
    var timeZone = location.defaultZone

    /** Daily studying intervals per day of week */
    // TODO doesn't compile
    //val studying = DayOfWeek.entries.take(if (age < 20) 5 else 0).associateWith { 8.hour / 16.hour }.toMutableMap()
    val studyTravel = 20.minutes

    /** Daily working intervals per day of week */
    //val working = DayOfWeek.entries.take(if (age < 20) 0 else 5).associateWith { 8.hour / 16.hour }.toMutableMap()
    val workingTravel = 20.minutes

    /** Free daily action intervals per day of week */
    //val action = DayOfWeek.entries.takeLast(2).associateWith { 9.hour / 17.hour }.toMutableMap()
}
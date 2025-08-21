package my.life.time

import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class TimeBuilderTest {
    @Test fun date_epoch() = test(date(), "1970-01-01")
    @Test fun date_me() = test(date(1983, 5, 4), "1983-05-04")

    @Test fun dayOfWeek() = test(1.dayOfWeek, "1")

    @Test fun time() = test("12:34".time, "12:34")

    companion object {
        fun test(actual: Time, expected: String) {
            val json = Json.encodeToString(actual)
            val quoted = "\"$expected\""
            assertEquals(quoted, json)
        }
    }
}
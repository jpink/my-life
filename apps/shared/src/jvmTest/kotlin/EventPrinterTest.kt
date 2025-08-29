package my.life.routine

import my.life.time.DayOfWeek
import kotlin.test.Test
import kotlin.test.assertEquals

class EventPrinterTest {
    @Test
    fun planMonday() {
        assertEquals("""
            0.00 > aamupala
            7.00 < astiat kaappiin
            7.10 > astiat kaappiin
            8.00 < hampaat
            8.04 > hampaat
            11.00 < lounas
            11.30 > lounas
            14.00 < sähkön hinta
            14.02 > sähkön hinta
            14.30 < välipala
            14.40 > välipala
            17.00 < päivällinen
            17.45 > päivällinen
            20.00 < iltapala
            20.20 > iltapala
            20.30 < astianpesu
            20.40 > astianpesu
            21.00 < hammasvälit
            21.05 > hammasvälit
            23.40 < aamupala
        """.trimIndent(), EventPrinter(example).printPlan(DayOfWeek.Monday))
    }
}
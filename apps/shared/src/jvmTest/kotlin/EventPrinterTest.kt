package my.life.routine

import my.life.time.Day
import kotlin.test.Test
import kotlin.test.assertEquals

class EventPrinterTest {
    @Test fun planMondayByAverage() = assertEquals("""
                 6:36 AM > uni
                 6:51 AM ) uni
                 6:52 AM < parta
                 6:57 AM > parta
                 6:58 AM < hampaat
                 7:02 AM > hampaat
                 7:03 AM < astiat kaappiin
                 7:13 AM > astiat kaappiin
                 7:14 AM < aamupala
                 7:34 AM > aamupala
                 7:35 AM ( työ
                 8:00 AM < työ
                11:00 AM < lounas
                11:30 AM > lounas
                 2:00 PM < sähkön hinta
                 2:02 PM > sähkön hinta
                 2:30 PM < välipala
                 2:40 PM > välipala
                 4:00 PM > työ
                 5:00 PM < päivällinen
                 5:45 PM > päivällinen
                 8:00 PM < iltapala
                 8:20 PM > iltapala
                 8:30 PM < astianpesu
                 8:40 PM > astianpesu
                 9:00 PM < hammasvälit
                 9:05 PM > hammasvälit
                10:36 PM < uni
        """.trimIndent(), EventPrinter(average).printPlan(Day.Monday))

    @Test fun planMondayForJukka() = assertEquals("""
                 6.36 > uni
                 6.51 ) uni
                 6.52 < parta
                 6.57 > parta
                 6.58 < hampaat
                 7.02 > hampaat
                 7.03 < astiat kaappiin
                 7.13 > astiat kaappiin
                 7.14 < aamupala
                 7.34 > aamupala
                 7.35 ( työ
                 8.00 < työ
                11.00 < lounas
                11.30 > lounas
                14.00 < sähkön hinta
                14.02 > sähkön hinta
                14.30 < välipala
                14.40 > välipala
                16.00 > työ
                16.25 ) työ
                17.00 < päivällinen
                17.45 > päivällinen
                20.00 < iltapala
                20.20 > iltapala
                20.30 < astianpesu
                20.40 > astianpesu
                21.00 < hammasvälit
                21.05 > hammasvälit
                22.51 < uni
        """.trimIndent(), EventPrinter(jukka).printPlan(Day.Monday))

    @Test fun planFridayForJukka() = assertEquals("""
                 6.36 > uni
                 6.51 ) uni
                 6.52 < parta
                 6.57 > parta
                 6.58 < hampaat
                 7.02 > hampaat
                 7.03 < astiat kaappiin
                 7.13 > astiat kaappiin
                 7.14 < aamupala
                 7.34 > aamupala
                 7.35 ( työ
                 8.00 < työ
                11.00 < lounas
                11.30 > lounas
                14.00 < sähkön hinta
                14.02 > sähkön hinta
                14.30 < välipala
                14.40 > välipala
                16.00 > työ
                16.25 ) työ
                17.00 < päivällinen
                17.45 > päivällinen
                20.00 < iltapala
                20.20 > iltapala
                20.30 < astianpesu
                20.40 > astianpesu
                21.00 < hammasvälit
                21.05 > hammasvälit
                22.51 < uni
        """.trimIndent(), EventPrinter(jukka).printPlan(Day.Friday))

    @Test fun planSaturdayForJukka() = assertEquals("""
                 0.01 < uni
                 8.01 > uni
                 8.16 ) uni
                 8.17 < parta
                 8.22 > parta
                 8.23 < hampaat
                 8.27 > hampaat
                 8.28 < astiat kaappiin
                 8.38 > astiat kaappiin
                 8.39 < aamupala
                 8.59 > aamupala
                 9.00 < viikonloppu
                11.00 < lounas
                11.30 > lounas
                14.00 < sähkön hinta
                14.02 > sähkön hinta
                14.30 < välipala
                14.40 > välipala
                15.00 > viikonloppu
                17.00 < päivällinen
                17.45 > päivällinen
                20.00 < iltapala
                20.20 > iltapala
                20.30 < astianpesu
                20.40 > astianpesu
                21.00 < hammasvälit
                21.05 > hammasvälit
        """.trimIndent(), EventPrinter(jukka).printPlan(Day.Saturday))

    @Test fun planSundayForJukka() = assertEquals("""
                 0.01 < uni
                 8.01 > uni
                 8.16 ) uni
                 8.17 < parta
                 8.22 > parta
                 8.23 < hampaat
                 8.27 > hampaat
                 8.28 < astiat kaappiin
                 8.38 > astiat kaappiin
                 8.39 < aamupala
                 8.59 > aamupala
                 9.00 < viikonloppu
                11.00 < lounas
                11.30 > lounas
                14.00 < sähkön hinta
                14.02 > sähkön hinta
                14.30 < välipala
                14.40 > välipala
                15.00 > viikonloppu
                17.00 < päivällinen
                17.45 > päivällinen
                20.00 < iltapala
                20.20 > iltapala
                20.30 < astianpesu
                20.40 > astianpesu
                21.00 < hammasvälit
                21.05 > hammasvälit
        """.trimIndent(), EventPrinter(jukka).printPlan(Day.Sunday))
}
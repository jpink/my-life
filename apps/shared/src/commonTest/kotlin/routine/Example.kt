package my.life.routine

import my.life.time.Date
import my.life.time.Month.*
import my.life.time.minutes

@Suppress("SpellCheckingInspection")
val example = configure("FI", Date(1983, 5, 4), {
    man = true
    townHouse = true
}) {
    // Title - Information
    // Nimeke - Tiedot

    daily("lääkkeet otettu", HEALTH) {
        duration = 1.minutes
    }
    daily("maski pesty", HEALTH) {
        before = BREAKFAST
        duration = 3.minutes
    }
    daily("vatsat treenattu", WELFARE) {
        duration = 10.minutes
    }
    daily("liikuttu", WELFARE) { // Mielummin aamulla
        duration = 30.minutes
    }
    daily("tavarasta luovuttu", ECONOMIC) { // https://www.is.fi/asuminen/art-2000010094660.html
        duration = 30.minutes
    }
    weekly("lasten pedit pedattu", ECONOMIC) {
        duration = 30.minutes
    }
    yearly("mikkeliairshow.fi", FREE_TIME) {
        startFormula = FixedTime(September)
    }
}
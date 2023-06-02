package com.icxcu.adsmartbandapp.screens.plotsFields.heartRate

import com.icxcu.adsmartbandapp.R

fun getHeartRateZones(
    heartRateValue: Double,
    age: Int
): Int {
    val maxHeartRate = 220 - age
    val percentageOfMax = 100 * heartRateValue / maxHeartRate
    val resource: Int
    when {
        (percentageOfMax < 60.0 && 50.0 >= percentageOfMax) -> {
            resource = R.drawable.heart_rate_zone_1
        }

        (percentageOfMax < 70.0 && 60.0 >= percentageOfMax) -> {
            resource = R.drawable.heart_rate_zone_2
        }

        (percentageOfMax < 80.0 && 70.0 >= percentageOfMax) -> {
            resource = R.drawable.heart_rate_zone_3
        }

        (percentageOfMax < 90.0 && 80.0 >= percentageOfMax) -> {
            resource = R.drawable.heart_rate_zone_4
        }

        (percentageOfMax < 100.0 && 90.0 >= percentageOfMax) -> {
            resource = R.drawable.heart_rate_zone_5
        }

        else -> {
            resource = R.drawable.heart_rate
        }
    }

    return resource
}

fun getReadableHeartRateZones(heartRateValue: Double, age: Int): String {
    val maxHeartRate = 220 - age
    val percentageOfMax = 100 * heartRateValue / maxHeartRate
    val resource: String
    when {
        (percentageOfMax < 60.0 && 50.0 >= percentageOfMax) -> {
            resource = "Very light"
        }

        (percentageOfMax < 70.0 && 60.0 >= percentageOfMax) -> {
            resource = "Light"
        }

        (percentageOfMax < 80.0 && 70.0 >= percentageOfMax) -> {
            resource = "Moderate"
        }

        (percentageOfMax < 90.0 && 80.0 >= percentageOfMax) -> {
            resource = "Hard"
        }

        (percentageOfMax < 100.0 && 90.0 >= percentageOfMax) -> {
            resource = "Maximum"
        }

        else -> {
            resource = "No zone"
        }
    }

    return resource
}

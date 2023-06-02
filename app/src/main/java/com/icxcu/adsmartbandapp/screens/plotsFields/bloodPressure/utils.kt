package com.icxcu.adsmartbandapp.screens.plotsFields.bloodPressure

import com.icxcu.adsmartbandapp.R
import com.icxcu.adsmartbandapp.screens.plotsFields.physicalActivity.getHours

val mapCategories = mapOf<String, Int>(
    "Normal" to R.drawable.normal_blood_pressure,
    "Elevated" to R.drawable.elevated_blood_pressure,
    "High Blood Pressure \n (Hypertension) Stage I" to R.drawable.hypertension_stage_1_blood_pressure,
    "High Blood Pressure \n(Hypertension) Stage 2" to R.drawable.hypertension_stage_2_blood_pressure,
    "Hypertensive Crisis" to R.drawable.hypertensive_crisis_blood_pressure,
    "no category" to R.drawable.blood_pressure_gauge
)

val mapToReadableCategories = mapOf(
    "Normal" to "Normal",
    "Elevated" to "Elevated",
    "High Blood Pressure \n (Hypertension) Stage I" to "High Blood Pressure",
    "High Blood Pressure \n(Hypertension) Stage 2" to "High Blood Pressure",
    "Hypertensive Crisis" to "Hypertensive Crisis",
    "no category" to "no category"
)


fun  getBloodPressureCategory(valueSystolic: Double = 0.0, valueDiastolic:Double = 0.0):String{
    return when {
        ((0 < valueSystolic && valueSystolic < 120) && (0 < valueDiastolic && valueDiastolic < 80)) -> "Normal"
        ((valueSystolic >= 120 && valueSystolic < 130) && (0 < valueDiastolic && valueDiastolic < 80)) -> "Elevated"
        (valueSystolic in 130.0..139.0 || (valueDiastolic in 80.0..89.0)) -> "High Blood Pressure \n (Hypertension) Stage I"
        (valueSystolic >= 180 && valueDiastolic >= 120) -> "Hypertensive Crisis"
        (valueSystolic >= 140 || valueDiastolic >= 90) -> "High Blood Pressure \n(Hypertension) Stage 2"
        else -> {
            "no category"
        }
    }

}

fun findFirstsIndexWithValueInDataList(value: Double, data: List<Double>): String {
    var hour = " "
    if (value > 0 && data.isNotEmpty()) {
        val filterIndexed = data.mapIndexed { index, d ->
            if (d == value) {
                index
            } else {
                -1
            }
        }.filter {
            it > -1
        }
        if (filterIndexed.isNotEmpty()) {
            hour = getHours()[filterIndexed[0]]
        }
    }

    return hour
}

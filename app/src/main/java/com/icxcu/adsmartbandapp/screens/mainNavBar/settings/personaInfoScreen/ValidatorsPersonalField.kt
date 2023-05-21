package com.icxcu.adsmartbandapp.screens.mainNavBar.settings.personaInfoScreen

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class ValidatorsPersonalField {

    companion object{
        val weightValidator = { number: String ->
            val numberD: Double
            var numberS: String = number
            try {
                numberD = number.toDouble()
                if (numberD < 0) {
                    numberS = ""
                }
            } catch (e: NumberFormatException) {
                numberS = ""
            }
            numberS
        }

        val heightValidator = { number: String ->
            val numberD: Double
            var numberS: String = number
            try {
                numberD = number.toDouble()
                if (numberD < 0) {
                    numberS = ""
                }
            } catch (e: NumberFormatException) {
                numberS = ""
            }
            numberS
        }

        val dateValidator = { currentDate: String ->
            val displayDate = if (currentDate == "") {
                ""
            } else {
                val trimmed = if (currentDate.length >= 8) currentDate.substring(0..7) else currentDate
                var out = ""
                for (i in trimmed.indices) {
                    out += trimmed[i]
                    if (i % 2 == 1 && i < 4) out += "/"
                }

                try {
                    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                    LocalDate.parse(out, formatter)
                }catch (e: DateTimeParseException){
                    out = "Enter a valid date"
                }
                out
            }
            displayDate

        }

    }
}
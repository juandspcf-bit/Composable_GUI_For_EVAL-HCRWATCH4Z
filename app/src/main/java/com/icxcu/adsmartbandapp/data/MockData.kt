package com.icxcu.adsmartbandapp.data

import com.icxcu.adsmartbandapp.repositories.Values
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class MockData {

    companion object{
        private val stepValues = listOf(
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            141,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            239,
            110,
            1455,
            3177,
            2404,
            246,
            315,
            65,
            25,
            74,
            0,
            0,
            0,
            47,
            77,
            1025,
            1600,
            164,
            252,
            37,
            51,
            79,
            0,
            11,
            0,
            17,
            43,
            311,
            0
        )

        private var distanceValues = listOf(
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.109,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.185,
            0.077,
            1.127,
            2.512,
            1.849,
            0.185,
            0.249,
            0.053,
            0.02,
            0.058,
            0.0,
            0.0,
            0.0,
            0.039,
            0.061,
            0.788,
            1.201,
            0.131,
            0.193,
            0.03,
            0.04,
            0.062,
            0.0,
            0.009,
            0.0,
            0.014,
            0.034,
            0.204,
            0.0
        )

        private var caloriesValues = listOf(
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            7.1,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            12.1,
            5.1,
            73.7,
            164.2,
            121.0,
            12.0,
            16.4,
            3.4,
            1.3,
            3.8,
            0.0,
            0.0,
            0.0,
            2.6,
            3.9,
            51.6,
            78.6,
            8.5,
            12.7,
            1.9,
            2.6,
            4.1,
            0.0,
            0.6,
            0.0,
            0.8,
            2.3,
            13.3,
            0.0
        )

        private val bloodPressureHighValues = listOf(116.6666667,
            120.3333333,
            120.1666667,
            121.6666667,
            121.1666667,
            120.0,
            119.0,
            120.6666667,
            122.5,
            120.3333333,
            118.0,
            119.8333333,
            120.3333333,
            116.8333333,
            119.1666667,
            121.5,
            120.8333333,
            122.0,
            120.6666667,
            121.8333333,
            119.1666667,
            120.5,
            119.6666667,
            122.3333333,
            120.3333333,
            119.1666667,
            123.8333333,
            121.5,
            121.8333333,
            121.6666667,
            118.8333333,
            120.3333333,
            122.8333333,
            123.0,
            120.0,
            119.0,
            120.0,
            123.0,
            124.3333333,
            120.0,
            117.8333333,
            122.3333333,
            121.0,
            123.0,
            118.8333333,
            125.0,
            119.4,
            0.0)

        private val bloodPressureLowValues = listOf(81.0,
            84.0,
            82.33333333,
            78.0,
            81.66666667,
            81.33333333,
            79.33333333,
            80.5,
            82.66666667,
            79.33333333,
            80.66666667,
            78.16666667,
            82.83333333,
            78.83333333,
            83.16666667,
            78.83333333,
            80.16666667,
            80.0,
            80.16666667,
            79.0,
            79.5,
            79.66666667,
            78.83333333,
            82.16666667,
            82.0,
            82.0,
            81.83333333,
            80.83333333,
            75.66666667,
            82.33333333,
            80.33333333,
            78.5,
            78.66666667,
            79.66666667,
            80.5,
            82.33333333,
            79.83333333,
            78.16666667,
            79.33333333,
            84.66666667,
            81.0,
            81.16666667,
            80.83333333,
            80.0,
            84.0,
            84.66666667,
            81.8,
            0.0)

        private val bloodPressureValues = bloodPressureHighValues.zip(bloodPressureLowValues) { high, low ->
            listOf(high, low)
        }.toList()

  /*      val date = Date()
        val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val dateData = formattedDate.format(date)*/

        var myDateObj = LocalDateTime.now()
        var myFormatObj = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        var formattedDate = myDateObj.format(myFormatObj)
        val valuesToday = Values(stepValues, distanceValues, caloriesValues, bloodPressureValues, formattedDate)
    }
}
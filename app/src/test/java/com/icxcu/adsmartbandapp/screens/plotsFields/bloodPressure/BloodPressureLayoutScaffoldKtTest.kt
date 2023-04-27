package com.icxcu.adsmartbandapp.screens.plotsFields.bloodPressure

import com.icxcu.adsmartbandapp.data.MockData
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach

internal class BloodPressureLayoutScaffoldKtTest {

    @BeforeEach
    fun initEach(){
        val systolic = MockData.valuesToday.systolic
    }

    @org.junit.jupiter.api.Test
    fun testFindIndex() {
        val input = listOf(0.0,0.0,0.0,1.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,)
        val hour = findIndex(1.0, input)
        assertEquals("1:30", hour)
    }
}
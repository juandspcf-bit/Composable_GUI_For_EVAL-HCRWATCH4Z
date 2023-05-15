package com.icxcu.adsmartbandapp.screens.plotsFields.physicalActivity

import android.util.Log
import java.time.Duration

fun getHours() = List(MutableList(48) { 0 }.size) { index ->
    val hour = "${Duration.ofHours(0).plusMinutes(30L * index).toHours()}"
    val time = hour + if ((index + 1) % 2 == 0) {
        ":30"
    } else {
        ":00"
    }
    time
}

fun getIntegerListFromStringMap(map: String): List<Int> {
    val newMap = map.subSequence(1, map.length - 1)
    val newMapD = newMap.split(", ")
    val stepsLists = MutableList(48) { 0 }
    newMapD.forEachIndexed { _, s ->
        val split = s.split("=")
        val index = split[0].toInt()
        val value = split[1].toInt()
        stepsLists[index] = value
    }
    Log.d("DIV", "getDataFromStringMap: $stepsLists")
    return stepsLists.toList()
}

fun getDoubleListFromStringMap(map: String): List<Double> {
    val newMap = map.subSequence(1, map.length - 1)
    val newMapD = newMap.split(", ")
    val stepsLists = MutableList(48) { 0.0 }
    newMapD.forEachIndexed { _, s ->
        val split = s.split("=")
        val index = split[0].toInt()
        val value = split[1].toDouble()
        stepsLists[index] = value
    }
    Log.d("DIV", "getDataFromStringMap: $stepsLists")
    return stepsLists.toList()
}

package com.icxcu.adsmartbandapp.screens

sealed class NavFieldsRoutes (val route: String) {
    object CardFields : NavFieldsRoutes("cardFields")
    object StepsPlots : NavFieldsRoutes("stepsPlot")

}
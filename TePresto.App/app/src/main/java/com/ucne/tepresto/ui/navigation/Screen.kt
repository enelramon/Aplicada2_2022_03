package com.ucne.tepresto.ui.navigation

sealed class Screen(val route: String) {
    object OcupacionList : Screen("OcupacionList")
    object OcupacionScreen: Screen("Ocupacion")
}
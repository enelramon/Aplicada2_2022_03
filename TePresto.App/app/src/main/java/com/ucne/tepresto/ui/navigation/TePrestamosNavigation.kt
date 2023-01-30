package com.ucne.tepresto.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.ucne.tepresto.ui.ocupacion.OcupacionListScreen
import com.ucne.tepresto.ui.ocupacion.OcupacionScreen

@Composable
fun PrestamosNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.OcupacionList.route
    ) {
        composable(Screen.OcupacionList.route) {
            OcupacionListScreen(
                onNavigateToOcupacion =
                { ocupacionId ->
                    navController.navigate(Screen.OcupacionScreen.route + "/$ocupacionId")
                })
        }
        composable(
            Screen.OcupacionScreen.route + "/{ocupacionId}",
            arguments = listOf(navArgument("ocupacionId") { type = NavType.IntType })
        ) { backStackEntry ->

            val ocupacionId = backStackEntry.arguments?.getInt("ocupacionId") ?: 0
            OcupacionScreen(
                onNavigateBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}
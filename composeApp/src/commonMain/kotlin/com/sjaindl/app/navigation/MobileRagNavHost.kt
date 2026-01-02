package com.sjaindl.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sjaindl.app.HomeScreen
import com.sjaindl.assistant.navigation.assistantGraph

@Composable
fun MobileRagNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {

    NavHost(
        navController = navController,
        startDestination = Route.Home,
        modifier = modifier,
    ) {
        composable<Route.Home> {
            HomeScreen()
        }

        assistantGraph(rootNavController = navController)
    }
}

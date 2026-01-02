package com.sjaindl.app.navigation

import androidx.navigation.NavBackStackEntry
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object Home: Route

    @Serializable
    data object ChatBot: Route
}

fun NavBackStackEntry?.toRoute(): Route? = when (this?.destination?.route?.substringAfterLast(".")?.substringBefore("/")) {
    Route.Home.toString() -> toRoute<Route.Home>()
    Route.ChatBot.toString() -> toRoute<Route.ChatBot>()

    else -> {
        null
    }
}

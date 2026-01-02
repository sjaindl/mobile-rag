package com.sjaindl.app

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.sjaindl.app.navigation.MobileRagNavHost
import com.sjaindl.app.navigation.Route
import com.sjaindl.app.navigation.toRoute
import com.sjaindl.assistant.ui.AssistantChat
import mobile_rag.composeapp.generated.resources.Res
import mobile_rag.composeapp.generated.resources.appName
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App() {
    val navController = rememberNavController()

    var currentRoute: Route? by remember(navController.currentBackStackEntry) {
        mutableStateOf(value = null)
    }

    navController.addOnDestinationChangedListener { controller, _, _ ->
        currentRoute = controller.currentBackStackEntry.toRoute()
    }

    MaterialTheme {
        Scaffold(
            topBar = {
                if (currentRoute != Route.ChatBot) {
                    Column {
                        TopAppBar(
                            title = {
                                Text(text = stringResource(Res.string.appName))
                            },
                        )
                    }
                }
            },
            floatingActionButton = {
                if (currentRoute != Route.ChatBot) {
                    AssistantChat(navController = navController)
                }
            }
        ) {
            MobileRagNavHost(
                navController = navController,
                modifier = if (currentRoute == Route.ChatBot) {
                    Modifier
                } else {
                    Modifier
                        .padding(paddingValues = it)
                }
            )
        }
    }
}

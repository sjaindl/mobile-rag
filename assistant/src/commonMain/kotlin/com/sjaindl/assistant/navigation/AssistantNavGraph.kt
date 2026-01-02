package com.sjaindl.assistant.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sjaindl.assistant.config.AssistantConfig
import com.sjaindl.assistant.ui.AssistantAppBar
import com.sjaindl.assistant.ui.ChatScreen
import com.sjaindl.assistant.ui.model.ChatUiState
import org.koin.compose.koinInject

const val chatScreenRoute = "ChatBot"

fun NavGraphBuilder.assistantGraph(
    rootNavController: NavController,
) {
    composable(route = chatScreenRoute) {
        val uiState = ChatUiState(
            isLoading = false,
            messages = emptyList(),
        )
        val config = koinInject<AssistantConfig>()

        Scaffold(
            topBar = {
                AssistantAppBar(
                    title = config.appBarTitle,
                    navigateUp = {
                        rootNavController.popBackStack()
                    },
                )
            }
        ) {
            ChatScreen(
                modifier = Modifier
                    .padding(paddingValues = it),
                uiState = uiState,
                onSendPrompt = {

                },
            )
        }
    }
}

fun NavController.navigateToAssistant() {
    navigate(chatScreenRoute)
}

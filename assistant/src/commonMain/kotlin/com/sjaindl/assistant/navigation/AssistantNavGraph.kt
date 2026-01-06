package com.sjaindl.assistant.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sjaindl.assistant.config.AssistantConfig
import com.sjaindl.assistant.ui.AssistantAppBar
import com.sjaindl.assistant.ui.ChatScreen
import com.sjaindl.assistant.ui.ChatViewModel
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject

const val chatScreenRoute = "ChatBot"

fun NavGraphBuilder.assistantGraph(
    rootNavController: NavController,
) {
    composable(route = chatScreenRoute) {
        val chatViewModel = koinInject<ChatViewModel>()

        val uiState by chatViewModel.uiState.collectAsStateWithLifecycle()
        val config = koinInject<AssistantConfig>()

        Scaffold(
            topBar = {
                AssistantAppBar(
                    title = config.appBarTitle,
                    icon = config.appBarIcon,
                    iconTint = config.appBarIconTint,
                    showResetOption = config.resetOption,
                    navigateUp = {
                        rootNavController.popBackStack()
                    },
                    onResetChat = chatViewModel::resetChat,
                )
            }
        ) {
            ChatScreen(
                modifier = Modifier
                    .padding(paddingValues = it),
                uiState = uiState,
                sampleQuestions = config.sampleQuestions.map { resource ->
                    stringResource(resource = resource)
                },
                welcomeMessage = config.welcomeMessage?.let { resource ->
                    stringResource(resource = resource)
                },
                assistantIcon = config.assistantIcon,
                userIcon = config.userIcon,
                messageCharLimit = config.messageCharLimit,
                promptPlaceholder = config.promptPlaceholder,
                onSendPrompt = chatViewModel::sendPrompt,
            )
        }
    }
}

fun NavController.navigateToAssistant() {
    navigate(chatScreenRoute)
}

package com.sjaindl.assistant.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.sjaindl.assistant.navigation.navigateToAssistant
import io.github.sjaindl.assistant.generated.resources.Res
import io.github.sjaindl.assistant.generated.resources.chat
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun AssistantChat(navController: NavController) {
    FloatingActionButton(
        onClick = {
            navController.navigateToAssistant()
        }
    ) {
        Icon(imageVector = Icons.Filled.Forum, contentDescription = stringResource(Res.string.chat))
    }
}

@Preview
@Composable
fun AssistantChatPreview() {
    val navController = rememberNavController()

    MaterialTheme {
        AssistantChat(navController = navController)
    }
}

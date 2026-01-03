package com.sjaindl.assistant.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedAssistChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.sjaindl.assistant.ui.theme.spacing
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun SampleQuestions(
    sampleQuestions: List<String>,
    onSendPrompt: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = spacing.md, vertical = spacing.lg),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(spacing.md)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(spacing.xxs),
        ) {
            sampleQuestions.forEach { question ->
                ElevatedAssistChip(
                    onClick = {
                        onSendPrompt(question)
                    },
                    label = {
                        Text(
                            text = question,
                            textAlign = TextAlign.Center,
                        )
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun SampleQuestionsPreview() {
    MaterialTheme {
        SampleQuestions(
            sampleQuestions = listOf(
                "What is the capital of India?",
                "What is the largest planet in our solar system?",
            ),
            onSendPrompt = { },
        )
    }
}

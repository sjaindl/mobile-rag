package com.sjaindl.app.di

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Title
import com.sjaindl.app.BuildConfig
import com.sjaindl.assistant.config.AssistantConfig
import com.sjaindl.assistant.config.ChatIcon
import com.sjaindl.assistant.config.Provider
import com.sjaindl.assistant.di.assistantModule
import mobile_rag.composeapp.generated.resources.Res
import mobile_rag.composeapp.generated.resources.appName
import mobile_rag.composeapp.generated.resources.sample_question_1
import mobile_rag.composeapp.generated.resources.sample_question_2
import mobile_rag.composeapp.generated.resources.welcome_message
import org.koin.dsl.module

val appModule = module {
    includes(assistantModule)

    single {
        AssistantConfig(
            provider = Provider.Flowise(
                baseUrl = "http://localhost:3000/flowise/api/v1/prediction/[chatflow-id]",
                apiKey = BuildConfig.FLOWISE_API_KEY,
            ),
            appBarTitle = Res.string.appName,
            appBarIcon = ChatIcon.Vector(imageVector = Icons.Default.Title),
            appBarIconTint = true,
            sampleQuestions = listOf(Res.string.sample_question_1, Res.string.sample_question_2),
            welcomeMessage = Res.string.welcome_message,
        )
    }
}

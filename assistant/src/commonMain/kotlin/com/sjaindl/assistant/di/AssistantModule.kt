package com.sjaindl.assistant.di

import com.sjaindl.assistant.data.AssistantRepositoryImpl
import com.sjaindl.assistant.data.remote.AssistantService
import com.sjaindl.assistant.data.remote.KtorAssistantService
import com.sjaindl.assistant.domain.AssistantRepository
import com.sjaindl.assistant.domain.usecase.GetAssistantCompletionUseCase
import com.sjaindl.assistant.ui.ChatViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val assistantModule = module {
    single<AssistantService> {
        KtorAssistantService()
    }

    single<AssistantRepository> {
        AssistantRepositoryImpl(assistantService = get())
    }

    factory {
        GetAssistantCompletionUseCase(repository = get())
    }

    single<GetAssistantCompletionUseCase> {
        GetAssistantCompletionUseCase(repository = get())
    }

    factory {
        ChatViewModel(getAssistantCompletionUseCase = get(), config = get())
    }

    factory {
        Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
            encodeDefaults = true
        }
    }

    factory {
        HttpClient {
            install(ContentNegotiation) {
                json(json = get())
            }
        }
    }
}

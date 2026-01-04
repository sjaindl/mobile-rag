package com.sjaindl.assistant.di

import com.sjaindl.assistant.data.AssistantRepositoryImpl
import com.sjaindl.assistant.data.ChatMessageDataSource
import com.sjaindl.assistant.data.remote.AssistantService
import com.sjaindl.assistant.data.remote.KtorAssistantService
import com.sjaindl.assistant.database.ChatDatabase
import com.sjaindl.assistant.database.createDatabase
import com.sjaindl.assistant.domain.AssistantRepository
import com.sjaindl.assistant.domain.usecase.ClearChatHistoryUseCase
import com.sjaindl.assistant.domain.usecase.GetAssistantCompletionUseCase
import com.sjaindl.assistant.domain.usecase.GetChatHistoryUseCase
import com.sjaindl.assistant.domain.usecase.InsertChatMessageUseCase
import com.sjaindl.assistant.domain.usecase.UpdateChatMessageUseCase
import com.sjaindl.assistant.ui.ChatViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.dsl.module

expect val platformModule: Module

val domainModule = module {
    factory {
        GetAssistantCompletionUseCase(repository = get())
    }

    factory {
        GetChatHistoryUseCase(repository = get())
    }

    factory {
        InsertChatMessageUseCase(repository = get())
    }

    factory {
        UpdateChatMessageUseCase(repository = get())
    }

    factory {
        ClearChatHistoryUseCase(repository = get())
    }
}

val dataModule = module {
    single<AssistantService> {
        KtorAssistantService()
    }

    single<AssistantRepository> {
        AssistantRepositoryImpl(
            assistantService = get(),
            chatMessageDataSource = get()
        )
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

    single<ChatDatabase> {
        createDatabase(driverFactory = get())
    }

    single {
        ChatMessageDataSource(db = get(), json = get())
    }
}

val assistantModule = module {
    includes(platformModule, dataModule, domainModule)

    factory {
        ChatViewModel(
            getAssistantCompletionUseCase = get(),
            getChatHistoryUseCase = get(),
            insertChatMessageUseCase = get(),
            updateChatMessageUseCase = get(),
            config = get(),
        )
    }
}

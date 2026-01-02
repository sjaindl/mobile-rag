package com.sjaindl.app.di

import com.sjaindl.assistant.config.AssistantConfig
import com.sjaindl.assistant.config.Provider
import com.sjaindl.assistant.di.assistantModule
import mobile_rag.composeapp.generated.resources.Res
import mobile_rag.composeapp.generated.resources.appName
import org.koin.dsl.module

val appModule = module {
    includes(assistantModule)

    single {
        AssistantConfig(
            provider = Provider.Flowise(
                baseUrl = "http://localhost:3000/flowise/api/v1/prediction/[chatflow-id]",
            ),
            appBarTitle = Res.string.appName,
        )
    }
}

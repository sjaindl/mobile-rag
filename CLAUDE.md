# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

**mobile-rag** is a Kotlin Multiplatform (KMP) library providing RAG (Retrieval-Augmented Generation) integration with a ready-to-use chatbot UI. The library integrates with [Flowise](https://flowiseai.com/) as the RAG backend. It targets Android, iOS, and Desktop (JVM) and is published to Maven Central as `io.github.sjaindl:mobile-rag-assistant`.

## Build Commands

```bash
# Build all modules
./gradlew build

# Run common/JVM tests
./gradlew :assistant:jvmTest

# Run Android host tests
./gradlew :assistant:androidHostTest

# Run a single test class
./gradlew :assistant:jvmTest --tests "com.sjaindl.assistant.TokenizerTest"

# Generate documentation
./gradlew :assistant:dokkaGeneratePublicationHtml

# Publish to Maven Central (requires local.properties signing config)
./gradlew :assistant:publish

# Run the desktop app
./gradlew :composeApp:run
```

## Local Configuration

Create `local.properties` in the root with:
```properties
FLOWISE_API_KEY=your_api_key_here

# For publishing only:
signing.keyId=...
signing.secretSigningKeyFile=path/to/key.gpg
signing.password=...
```

The Flowise backend domain is configured in `composeApp/src/commonMain/kotlin/com/sjaindl/app/di/KoinInit.kt` and `assistant/src/commonMain/kotlin/com/sjaindl/assistant/config/DOMAIN.kt`.

## Module Structure

```
androidApp      → composeApp → assistant (core library)
iosApp (Xcode)  → composeApp (as KMP framework)
```

- **`:assistant`** — The publishable library. Contains all core logic: Compose UI, domain use cases, data layer (Ktor + SQLDelight), and Koin DI modules.
- **`:composeApp`** — Shared multiplatform app shell. Wires `AssistantConfig` with the actual Flowise URL/API key via BuildKonfig. Targets Android, iOS, and JVM Desktop.
- **`:androidApp`** — Android application entry point (`MainActivity`). Depends on `:composeApp`.
- **`iosApp/`** — Xcode project that links the KMP framework built from `:composeApp`.

## Architecture

The `:assistant` module follows clean architecture:

```
UI Layer        → ChatScreen / ChatViewModel (Compose + MVVM, StateFlow)
Domain Layer    → Use cases (GetAssistantCompletionUseCase, etc.)
Data Layer      → AssistantRepositoryImpl
                  ├─ Remote: KtorAssistantService (Flowise SSE streaming)
                  └─ Local:  ChatMessageDataSource (SQLDelight)
DI              → Koin modules (assistantModule, dataModule, domainModule, platformModule)
```

### Key patterns

- **expect/actual**: Platform-specific code lives in `androidMain`, `iosMain`, `jvmMain`. Each platform provides its own `PlatformModule` (Koin), Ktor client engine, and SQLDelight driver.
- **Flowise SSE streaming**: `KtorAssistantService` reads an SSE stream from Flowise line-by-line. Each `data:` line is deserialized into a `FlowiseResponse` sealed class with a `@JsonClassDiscriminator("event")`.
- **Use cases use `operator fun invoke`**: Callers use `useCase(args)` syntax.
- **SQLDelight schema**: Defined in `assistant/src/commonMain/sqldelight/`. Generated code lands in package `com.sjaindl.assistant.database`.

### Library configuration

Consumers configure the library by constructing `AssistantConfig` and passing it to Koin during app startup. See `composeApp/src/commonMain/kotlin/com/sjaindl/app/di/AppModule.kt` for the reference integration.

## Key Dependencies

| Dependency | Purpose |
|---|---|
| Ktor 3.x | HTTP client (SSE streaming to Flowise) |
| SQLDelight | Local chat history persistence |
| Koin 4.x | Dependency injection (multiplatform) |
| Compose Multiplatform | Shared UI |
| Kotlinx Serialization | JSON parsing of Flowise responses |
| Napier | Multiplatform logging |
| Coil 3.x | Image loading in Compose |
| BuildKonfig | Injects `local.properties` values into common code at build time |

## Publishing

The library version is set in `assistant/build.gradle.kts` (`version = "1.0.3"`). Publishing uses Vanniktech Maven Publish with PGP signing. All signing credentials come from `local.properties`.
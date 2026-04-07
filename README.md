# mobile-rag

A Kotlin Multiplatform (KMP) library providing RAG (Retrieval-Augmented Generation) integration with a ready-to-use chatbot UI. The library integrates with [Flowise](https://flowiseai.com/) as the RAG backend and targets **Android**, **iOS**, and **Desktop (JVM)**.

Published to Maven Central as `io.github.sjaindl:mobile-rag-assistant`.

## Features

- Ready-to-use Compose Multiplatform chat UI
- SSE streaming responses from Flowise
- Local chat history persistence via SQLDelight
- Configurable app bar title and icon
- Optional welcome message and sample questions
- Source documents and tool call display
- Message reset option
- Customizable user and assistant icons
- Message character limit

## Installation

Add the dependency to your `build.gradle.kts`:

```kotlin
implementation("io.github.sjaindl:mobile-rag-assistant:1.0.3")
```

### Requirements

- Android minSdk 24+
- iOS (iosX64, iosArm64, iosSimulatorArm64)
- JVM Desktop

## Usage

### 1. Configure Koin

Initialize Koin at app startup and provide an `AssistantConfig`:

```kotlin
startKoin {
    modules(assistantModule)
    single {
        AssistantConfig(
            provider = Provider.Flowise(
                baseUrl = "https://your-flowise-domain/flowise/api/v1/prediction/<chatflow-id>",
                apiKey = "your_api_key", // optional
            ),
            appBarTitle = Res.string.appName,
        )
    }
}
```

### 2. Wire up navigation

The library provides an `assistantGraph` extension on `NavGraphBuilder` and a `navigateToAssistant()` extension on `NavController` for integration into your existing Compose Navigation setup:

```kotlin
NavHost(navController = navController, startDestination = "home") {
    composable("home") { HomeScreen() }

    assistantGraph(rootNavController = navController)
}
```

Navigate to the chat screen from anywhere:

```kotlin
navController.navigateToAssistant()
```

### 3. AssistantConfig options

| Parameter | Type | Default | Description |
|---|---|---|---|
| `provider` | `Provider` | required | Backend provider (currently `Provider.Flowise`) |
| `appBarTitle` | `StringResource` | required | Title shown in the chat app bar |
| `appBarIcon` | `ChatIcon?` | `null` | Icon shown in the app bar |
| `appBarIconTint` | `Boolean` | `false` | Whether to tint the app bar icon |
| `streaming` | `Boolean` | `true` | Enable SSE streaming responses |
| `streamingDelayMilliseconds` | `Long` | `4L` | Delay between streamed chunks (ms) |
| `showTools` | `Boolean` | `true` | Show tool/agent call info in chat |
| `showSourceDocuments` | `Boolean` | `true` | Show source document references |
| `welcomeMessage` | `StringResource?` | `null` | Message shown at the start of a new chat |
| `sampleQuestions` | `List<StringResource>` | `[]` | Suggested questions displayed to the user |
| `persistMessages` | `Boolean` | `true` | Persist messages locally with SQLDelight |
| `resetOption` | `Boolean` | `true` | Show reset/clear chat button |
| `assistantIcon` | `ChatIcon` | Chat icon | Icon shown next to assistant messages |
| `userIcon` | `ChatIcon` | Person icon | Icon shown next to user messages |
| `promptPlaceholder` | `StringResource` | `"Prompt"` | Placeholder in the message input field |
| `messageCharLimit` | `Int` | `250` | Maximum characters per message |

`ChatIcon` can be either a Compose `ImageVector` or a `DrawableResource`:

```kotlin
ChatIcon.Vector(imageVector = Icons.Default.MyIcon)
ChatIcon.Drawable(drawable = Res.drawable.my_icon)
```

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

Platform-specific code (Ktor client engine, SQLDelight driver, Koin platform module) lives in `androidMain`, `iosMain`, and `jvmMain` via Kotlin `expect`/`actual`.

## Module Structure

```
androidApp      → composeApp → assistant (core library)
iosApp (Xcode)  → composeApp (as KMP framework)
```

| Module | Description |
|---|---|
| `:assistant` | Publishable library — core logic, Compose UI, use cases, data layer, Koin DI |
| `:composeApp` | Shared multiplatform app shell — wires `AssistantConfig` via BuildKonfig |
| `:androidApp` | Android application entry point (`MainActivity`) |
| `iosApp/` | Xcode project linking the KMP framework from `:composeApp` |

## Key Dependencies

| Dependency | Version | Purpose |
|---|---|---|
| Kotlin Multiplatform | 2.3.20 | Multiplatform build |
| Compose Multiplatform | 1.10.3 | Shared UI |
| Ktor | 3.4.2 | HTTP client (SSE streaming to Flowise) |
| SQLDelight | 2.3.2 | Local chat history persistence |
| Koin | 4.2.0 | Dependency injection (multiplatform) |
| Kotlinx Serialization | 1.10.0 | JSON parsing of Flowise responses |
| Napier | 2.7.1 | Multiplatform logging |
| Coil | 3.4.0 | Image loading in Compose |
| BuildKonfig | 6.0.9 | Injects `local.properties` values into common code |

## Local Development

### 1. Local configuration

Create `local.properties` in the project root:

```properties
FLOWISE_API_KEY=your_api_key_here

# For publishing only:
signing.keyId=...
signing.secretSigningKeyFile=path/to/key.gpg
signing.password=...
```

### 2. Flowise domain

The Flowise backend URL is configured in:
- `composeApp/src/commonMain/kotlin/com/sjaindl/app/di/KoinInit.kt`
- `assistant/src/commonMain/kotlin/com/sjaindl/assistant/config/DOMAIN.kt`

### 3. Build commands

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

## Publishing

The library is published to Maven Central using the Vanniktech Maven Publish plugin with PGP signing.

- **Group**: `io.github.sjaindl`
- **Artifact**: `mobile-rag-assistant`
- **Version**: set in `assistant/build.gradle.kts`
- **License**: MIT

## License

[MIT License](https://opensource.org/license/mit)

## Author

[Stefan Jaindl](https://github.com/sjaindl)

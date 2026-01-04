package com.sjaindl.assistant.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sjaindl.assistant.config.AssistantConfig
import com.sjaindl.assistant.data.remote.model.FlowiseResponse
import com.sjaindl.assistant.domain.usecase.ClearChatHistoryUseCase
import com.sjaindl.assistant.domain.usecase.GetAssistantCompletionUseCase
import com.sjaindl.assistant.domain.usecase.GetChatHistoryUseCase
import com.sjaindl.assistant.domain.usecase.InsertChatMessageUseCase
import com.sjaindl.assistant.domain.usecase.UpdateChatMessageUseCase
import com.sjaindl.assistant.ui.model.ChatMessage
import com.sjaindl.assistant.ui.model.ChatUiState
import com.sjaindl.assistant.util.generateUuid
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChatViewModel(
    private val getAssistantCompletionUseCase: GetAssistantCompletionUseCase,
    private val getChatHistoryUseCase: GetChatHistoryUseCase,
    private val insertChatMessageUseCase: InsertChatMessageUseCase,
    private val updateChatMessageUseCase: UpdateChatMessageUseCase,
    private val clearChatHistoryUseCase: ClearChatHistoryUseCase,
    private val config: AssistantConfig,
) : ViewModel() {

    private val _uiState: MutableStateFlow<ChatUiState> = MutableStateFlow(ChatUiState())

    val uiState = _uiState
        .onStart {
            _uiState.update {
                it.copy(messages = getChatHistoryUseCase())
            }

        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
            initialValue = ChatUiState(),
        )

    private var chatId: String = generateUuid()

    fun sendPrompt(prompt: String) {
        viewModelScope.launch {
            val userMessage = ChatMessage(text = prompt, isFromUser = true)
            insertChatMessageUseCase(message = userMessage)

            _uiState.update {
                it.copy(
                    isLoading = true,
                    error = null,
                    messages = it.messages + userMessage
                )
            }

            var currentResponseMessageId: Long? = null

            getAssistantCompletionUseCase(prompt = prompt, chatId = chatId, streaming = config.streaming)
                .onEach { response ->
                    _uiState.update { currentState ->
                        when (response) {
                            is FlowiseResponse.FullResponseData -> {
                                var responseMessage = ChatMessage(
                                    text = response.text,
                                    isFromUser = false,
                                )

                                val newId = insertChatMessageUseCase(message = responseMessage)
                                currentResponseMessageId = newId
                                responseMessage = responseMessage.copy(id = newId)

                                _uiState.value.copy(
                                    messages = _uiState.value.messages + responseMessage
                                )
                            }

                            is FlowiseResponse.Start -> {
                                currentState.copy(isLoading = true)
                            }

                            is FlowiseResponse.Token -> {
                                val lastMessage = currentState.messages.last()

                                if (lastMessage.isFromUser) {
                                    var responseMessage = ChatMessage(
                                        text = response.data,
                                        isFromUser = false,
                                        isTyping = true
                                    )

                                    val newId = insertChatMessageUseCase(message = responseMessage)
                                    currentResponseMessageId = newId
                                    responseMessage = responseMessage.copy(id = newId)

                                    currentState.copy(
                                        isLoading = false,
                                        messages = currentState.messages + responseMessage
                                    )
                                } else {
                                    val updatedMessages = currentState.messages.toMutableList()
                                    val updatedMessage = lastMessage.copy(text = lastMessage.text + response.data)

                                    updatedMessages[updatedMessages.lastIndex] = updatedMessage
                                    currentState.copy(messages = updatedMessages)
                                }
                            }

                            is FlowiseResponse.End -> {
                                currentResponseMessageId?.let { id ->
                                    val lastMessage = currentState.messages.last()

                                    val updatedMessages = currentState.messages.toMutableList()
                                    updatedMessages[updatedMessages.lastIndex] = lastMessage.copy(isTyping = false)
                                    updateChatMessageUseCase(id = id, message = lastMessage)

                                    currentState.copy(messages = updatedMessages)
                                } ?: currentState

                            }

                            is FlowiseResponse.Metadata -> {
                                chatId = response.data.chatId
                                currentState
                            }

                            is FlowiseResponse.UsedTools -> {
                                if (config.showTools) {
                                    val lastMessage = currentState.messages.last()

                                    val updatedMessages = currentState.messages.toMutableList()
                                    updatedMessages[currentState.messages.lastIndex] = lastMessage.copy(usedTools = response.data)
                                    currentState.copy(messages = updatedMessages)

                                } else {
                                    currentState
                                }
                            }

                            is FlowiseResponse.SourceDocuments -> {
                                if (config.showSourceDocuments) {
                                    val lastMessage = currentState.messages.last()

                                    val updatedMessages = currentState.messages.toMutableList()
                                    updatedMessages[currentState.messages.lastIndex] = lastMessage.copy(sourceDocuments = response.data)
                                    currentState.copy(messages = updatedMessages)
                                } else {
                                    currentState
                                }
                            }

                            is FlowiseResponse.Error -> {
                                currentState.copy(error = response.data)
                            }
                        }
                    }

                    delay(config.streamingDelayMilliseconds)
                }
                .catch { e ->
                    _uiState.update {
                        it.copy(error = e.message, isLoading = false)
                    }
                }
                .onCompletion {
                    _uiState.value = _uiState.value.copy(isLoading = false)
                }
                .collect()
        }
    }

    fun resetChat() {
        viewModelScope.launch {
            if (config.persistMessages) {
                clearChatHistoryUseCase()
            }

            _uiState.update {
                it.copy(messages = emptyList())
            }
        }
    }
}

package com.sjaindl.assistant.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sjaindl.assistant.config.AssistantConfig
import com.sjaindl.assistant.data.remote.model.FlowiseResponse
import com.sjaindl.assistant.domain.usecase.GetAssistantCompletionUseCase
import com.sjaindl.assistant.ui.model.ChatMessage
import com.sjaindl.assistant.ui.model.ChatUiState
import com.sjaindl.assistant.util.generateUUID
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChatViewModel(
    private val getAssistantCompletionUseCase: GetAssistantCompletionUseCase,
    private val config: AssistantConfig,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState = _uiState.asStateFlow()

    private var chatId: String = generateUUID()

    fun sendPrompt(prompt: String) {
        viewModelScope.launch {
            val userMessage = ChatMessage(text = prompt, isFromUser = true)

            _uiState.update {
                it.copy(
                    isLoading = true,
                    error = null,
                    messages = it.messages + userMessage
                )
            }

            getAssistantCompletionUseCase(prompt = prompt, chatId = chatId, streaming = config.streaming)
                .onEach { response ->
                    _uiState.update { currentState ->
                        when (response) {
                            is FlowiseResponse.FullResponseData -> {
                                _uiState.value.copy(
                                    messages = _uiState.value.messages + ChatMessage(
                                        text = response.text,
                                        isFromUser = false,
                                    )
                                )
                            }

                            is FlowiseResponse.Start -> {
                                currentState.copy(isLoading = true)
                            }

                            is FlowiseResponse.Token -> {
                                val lastMessage = currentState.messages.last()

                                if (lastMessage.isFromUser) {
                                    val aiMessage = ChatMessage(
                                        text = response.data,
                                        isFromUser = false,
                                        isTyping = true
                                    )

                                    currentState.copy(
                                        isLoading = false,
                                        messages = currentState.messages + aiMessage
                                    )
                                } else {
                                    val updatedMessages = currentState.messages.toMutableList()
                                    updatedMessages[updatedMessages.lastIndex] = lastMessage.copy(text = lastMessage.text + response.data)
                                    currentState.copy(messages = updatedMessages)
                                }
                            }

                            is FlowiseResponse.End -> {
                                val lastMessage = currentState.messages.last()

                                val updatedMessages = currentState.messages.toMutableList()
                                updatedMessages[updatedMessages.lastIndex] = lastMessage.copy(isTyping = false)
                                currentState.copy(messages = updatedMessages)
                            }

                            is FlowiseResponse.Metadata -> {
                                chatId = response.data.chatId
                                currentState
                            }

                            is FlowiseResponse.Error -> {
                                currentState.copy(error = response.data)
                            }
                        }
                    }

                    delay(config.streamingDelayMilliseconds)
                }
                .catch { e ->
                    _uiState.update { it.copy(error = e.message, isLoading = false) }
                }
                .onCompletion {
                    _uiState.value = _uiState.value.copy(isLoading = false)
                }
                .collect()
        }
    }
}

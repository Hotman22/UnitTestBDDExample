package com.otdev.unittestbddexemple.presentation.view.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.otdev.unittestbddexemple.core.CoroutineDispatcherProvider
import com.otdev.unittestbddexemple.domain.model.User
import com.otdev.unittestbddexemple.domain.usecase.interfaces.FakeUseCase
import com.otdev.unittestbddexemple.domain.usecase.interfaces.GetChatMessagesUseCase
import com.otdev.unittestbddexemple.domain.usecase.interfaces.SendTextMessageUseCase
import com.otdev.unittestbddexemple.presentation.model.MessageUiModel
import com.otdev.unittestbddexemple.presentation.model.mapper.MessageUiModelMapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ChatViewModel(
    private val getChatMessagesUseCase: GetChatMessagesUseCase,
    private val sendTextMessageUseCase: SendTextMessageUseCase,
    private val user: User,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider,
) : ViewModel() {

    data class State(
        val messages: List<MessageUiModel>
    )

    private val _state = MutableStateFlow(State(emptyList()))
    val state: StateFlow<State> = _state.asStateFlow()

    init {
        viewModelScope.launch(coroutineDispatcherProvider.main) {
            getChatMessagesUseCase(user).map {
                State(MessageUiModelMapper.fromMessagesList(it))
            }.collect { newState ->
                _state.value = newState
            }
        }
    }

    fun onTextMessageSent(text: String) = viewModelScope.launch(
        coroutineDispatcherProvider.main
    ) {
        sendTextMessageUseCase(text, user)
    }
}

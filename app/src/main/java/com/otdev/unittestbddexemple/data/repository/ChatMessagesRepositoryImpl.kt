package com.otdev.unittestbddexemple.data.repository

import com.otdev.unittestbddexemple.data.datasource.ChatDataSource
import com.otdev.unittestbddexemple.domain.model.Message
import com.otdev.unittestbddexemple.domain.model.User
import com.otdev.unittestbddexemple.domain.repository.ChatMessagesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

class ChatMessagesRepositoryImpl(
    private val chatDataSource: ChatDataSource
): ChatMessagesRepository {
    private val _cachedChatMessages = MutableStateFlow<List<Message>>(emptyList())

    override fun getChatMessages(user: User): Flow<List<Message>> = flow {
        if (_cachedChatMessages.value.isEmpty()) {
            _cachedChatMessages.emit(chatDataSource.getChatMessages(user))
        }
        emitAll(_cachedChatMessages)
    }

    override suspend fun sendChatMessage(message: Message, user: User) {
        _cachedChatMessages.apply { value = value + message }
        chatDataSource.sendChatMessage(message, user)
    }
}

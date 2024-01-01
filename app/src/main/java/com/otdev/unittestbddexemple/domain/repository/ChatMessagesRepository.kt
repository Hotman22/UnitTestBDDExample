package com.otdev.unittestbddexemple.domain.repository

import com.otdev.unittestbddexemple.domain.model.Message
import com.otdev.unittestbddexemple.domain.model.User
import kotlinx.coroutines.flow.Flow

interface ChatMessagesRepository {
    fun getChatMessages(user: User): Flow<List<Message>>
    suspend fun sendChatMessage(message: Message, user: User)
}

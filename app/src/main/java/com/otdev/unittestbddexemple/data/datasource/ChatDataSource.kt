package com.otdev.unittestbddexemple.data.datasource

import com.otdev.unittestbddexemple.domain.model.Message
import com.otdev.unittestbddexemple.domain.model.User

interface ChatDataSource {
    suspend fun getChatMessages(user: User): List<Message>
    suspend fun sendChatMessage(message: Message, user: User)
}

package com.otdev.unittestbddexemple.utils.datasource

import com.otdev.unittestbddexemple.data.datasource.ChatDataSource
import com.otdev.unittestbddexemple.domain.model.Message
import com.otdev.unittestbddexemple.domain.model.User

class FakeChatDataSource : ChatDataSource {
    private var messagesList = mutableListOf<Message>()

    override suspend fun getChatMessages(user: User): List<Message> {
        return messagesList
    }

    override suspend fun sendChatMessage(message: Message, user: User) {
        messagesList.add(message)
    }

    fun setResponse(messagesList: List<Message>) {
        this.messagesList = messagesList.toMutableList()
    }
}

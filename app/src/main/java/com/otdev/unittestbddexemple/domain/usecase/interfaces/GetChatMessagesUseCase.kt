package com.otdev.unittestbddexemple.domain.usecase.interfaces

import com.otdev.unittestbddexemple.domain.model.Message
import com.otdev.unittestbddexemple.domain.model.User
import com.otdev.unittestbddexemple.domain.repository.ChatMessagesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface GetChatMessagesUseCase {
    operator fun invoke(user: User): Flow<List<Message>>
}

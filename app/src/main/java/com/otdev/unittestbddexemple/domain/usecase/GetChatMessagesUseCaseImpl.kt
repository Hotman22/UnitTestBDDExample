package com.otdev.unittestbddexemple.domain.usecase

import com.otdev.unittestbddexemple.domain.model.Message
import com.otdev.unittestbddexemple.domain.model.User
import com.otdev.unittestbddexemple.domain.repository.ChatMessagesRepository
import com.otdev.unittestbddexemple.domain.usecase.interfaces.GetChatMessagesUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetChatMessagesUseCaseImpl(
    private val chatMessagesRepository: ChatMessagesRepository
): GetChatMessagesUseCase {

    override operator fun invoke(user: User): Flow<List<Message>> {
        return chatMessagesRepository.getChatMessages(user)
    }
}

package com.otdev.unittestbddexemple.domain.usecase

import com.otdev.unittestbddexemple.domain.factory.MessageFactory
import com.otdev.unittestbddexemple.domain.model.User
import com.otdev.unittestbddexemple.domain.repository.ChatMessagesRepository
import com.otdev.unittestbddexemple.domain.usecase.interfaces.SendTextMessageUseCase

class SendTextMessageUseCaseImpl(
    private val chatMessagesRepository: ChatMessagesRepository
): SendTextMessageUseCase {

    override suspend operator fun invoke(text: String, user: User) {
        if (isValid(text)) {
            val message = MessageFactory.fromText(text)
            chatMessagesRepository.sendChatMessage(message, user)
        }
    }

    private fun isValid(text: String): Boolean {
        return text.length < 180
    }
}

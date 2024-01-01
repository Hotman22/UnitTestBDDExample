package com.otdev.unittestbddexemple.mockTest.domain.usecase

import com.otdev.unittestbddexemple.domain.factory.MessageFactory
import com.otdev.unittestbddexemple.domain.model.Message
import com.otdev.unittestbddexemple.domain.model.User
import com.otdev.unittestbddexemple.domain.repository.ChatMessagesRepository
import com.otdev.unittestbddexemple.domain.usecase.SendTextMessageUseCaseImpl
import com.otdev.unittestbddexemple.domain.usecase.interfaces.SendTextMessageUseCase
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test


class SendTextMessageUseCaseImplTest {
    private val chatMessagesRepository: ChatMessagesRepository = mockk()
    private val user: User = User("id", "name")
    private var useCase: SendTextMessageUseCase = SendTextMessageUseCaseImpl(chatMessagesRepository)

    @Test
    fun `It sends a valid text message`() = runTest {
        val text = "Message"
        val message = MessageFactory.fromText(text)
        coEvery { chatMessagesRepository.sendChatMessage(message, user) } just Runs

        useCase(text, user)

        coVerify { chatMessagesRepository.sendChatMessage(message, user) }
    }


    @Test
    fun `It does not send an invalid text message`() = runTest {
        val invalidTextMessage = (1..180).joinToString("") { "a" }
        coEvery { chatMessagesRepository.sendChatMessage(any(), user) } just Runs

        useCase(invalidTextMessage, user)

        coVerify(exactly = 0) { chatMessagesRepository.sendChatMessage(any(), user) }
    }
}
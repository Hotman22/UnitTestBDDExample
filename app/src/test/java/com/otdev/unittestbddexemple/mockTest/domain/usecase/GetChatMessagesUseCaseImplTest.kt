package com.otdev.unittestbddexemple.mockTest.domain.usecase

import com.otdev.unittestbddexemple.domain.model.Message
import com.otdev.unittestbddexemple.domain.model.User
import com.otdev.unittestbddexemple.domain.repository.ChatMessagesRepository
import com.otdev.unittestbddexemple.domain.usecase.GetChatMessagesUseCaseImpl
import com.otdev.unittestbddexemple.domain.usecase.interfaces.GetChatMessagesUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.util.Date
import kotlin.test.assertEquals

class GetChatMessagesUseCaseImplTest {
    private val chatMessagesRepository: ChatMessagesRepository = mockk()
    private val user: User = User("id", "name")
    private var useCase: GetChatMessagesUseCase = GetChatMessagesUseCaseImpl(chatMessagesRepository)

    @Test
    fun `It emits the list of chat messages`() =
        runTest {
            val messages = listOf(Message("id", "1st message", Date(), Message.Type.Received))
            val response = flowOf(messages)
            every { chatMessagesRepository.getChatMessages(user) } returns response

            useCase.invoke(user)

            verify { chatMessagesRepository.getChatMessages(user) }
            assertEquals(useCase(user), response)
        }
}
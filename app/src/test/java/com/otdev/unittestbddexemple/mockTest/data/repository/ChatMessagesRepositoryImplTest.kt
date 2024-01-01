package com.otdev.unittestbddexemple.mockTest.data.repository

import app.cash.turbine.test
import com.otdev.unittestbddexemple.data.datasource.ChatDataSource
import com.otdev.unittestbddexemple.data.repository.ChatMessagesRepositoryImpl
import com.otdev.unittestbddexemple.domain.factory.MessageFactory
import com.otdev.unittestbddexemple.domain.model.Message
import com.otdev.unittestbddexemple.domain.model.User
import com.otdev.unittestbddexemple.domain.repository.ChatMessagesRepository
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.util.Date
import kotlin.test.assertEquals

class ChatMessagesRepositoryImplTest {
    private val chatDataSource: ChatDataSource = mockk()
    private val user: User = User("id", "name")
    private var repository: ChatMessagesRepository = ChatMessagesRepositoryImpl(chatDataSource)

    @Test
    fun `It emits the list of chat messages`() =
        runTest {
            val messages = listOf(Message("id", "1st message", Date(), Message.Type.Received))
            fakeDataSourceResponse(messages)

            repository.getChatMessages(user).test {
                val item = awaitItem()
                assertEquals(messages, item)
            }

            coVerify(exactly = 1) { chatDataSource.getChatMessages(user) }
        }

    @Test
    fun `It sends a chat message`() =
        runTest {
            val messages = listOf(
                Message("id", "1st message", Date(), Message.Type.Received),
                Message("id", "2nd message", Date(), Message.Type.Received)
            )
            fakeDataSourceResponse(messages)
            coEvery { chatDataSource.sendChatMessage(messages[1], user) }  just Runs

            repository.sendChatMessage(messages[1], user)

            coVerify { chatDataSource.sendChatMessage(messages[1], user) }
        }

    private fun fakeDataSourceResponse(messages: List<Message>) {
        coEvery { chatDataSource.getChatMessages(user) } returns messages
    }
}
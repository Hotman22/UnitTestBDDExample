package com.otdev.unittestbddexemple.mockTest.presentation.view.viewmodel

import app.cash.turbine.test
import com.otdev.unittestbddexemple.core.CoroutineDispatcherProvider
import com.otdev.unittestbddexemple.domain.model.Message
import com.otdev.unittestbddexemple.domain.model.User
import com.otdev.unittestbddexemple.domain.usecase.interfaces.FakeUseCase
import com.otdev.unittestbddexemple.domain.usecase.interfaces.GetChatMessagesUseCase
import com.otdev.unittestbddexemple.domain.usecase.interfaces.SendTextMessageUseCase
import com.otdev.unittestbddexemple.presentation.model.MessageUiModel
import com.otdev.unittestbddexemple.presentation.view.viewmodel.ChatViewModel
import com.otdev.unittestbddexemple.utils.CoroutinesTestRule
import com.otdev.unittestbddexemple.utils.common.TestCoroutineDispatcherProvider
import io.mockk.Awaits
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import java.util.Date
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class ChatViewModelMockTest {

    private val getChatMessagesUseCase: GetChatMessagesUseCase = mockk()
    private val sendTextMessageUseCase: SendTextMessageUseCase = mockk()
    private val fakeUseCase: FakeUseCase = mockk()
    private val coroutineDispatcherProvider = TestCoroutineDispatcherProvider()
    private val user: User = User("id", "name")
    private lateinit var viewModel: ChatViewModel

    @Test
    fun `It emits the state`() =
        runTest {
            val messages = listOf(Message("id", "1st message", Date(), Message.Type.Received))
            val messageUI = listOf(MessageUiModel("1st message", type = Message.Type.Received))
            val stateResponse = ChatViewModel.State(messages = messageUI)
            fakeDataSourceResponse(messages)

            initViewModel()

            viewModel.state.test {
                val item = awaitItem()
                assertEquals(item, stateResponse)
            }
            verify(exactly = 1) { getChatMessagesUseCase.invoke(user) }
        }

    @Test
    fun `It sends a text message`() =
        runTest {
            val messages = listOf(
                Message("id", "1st message", Date(), Message.Type.Received),
                Message("id", "2nd message", Date(), Message.Type.Received)
            )
            val textMessage = "2nd message"
            val stateResponse = ChatViewModel.State(
                messages = listOf(
                    MessageUiModel(
                        text = "1st message",
                        type = Message.Type.Received
                    ),
                    MessageUiModel(
                        text = "2nd message", type = Message.Type.Received
                    )
                )
            )
            fakeDataSourceResponse(messages)
            coEvery { sendTextMessageUseCase.invoke(textMessage, user) } just Runs

            initViewModel()
            viewModel.onTextMessageSent(textMessage)

            viewModel.state.test {
                val item = awaitItem()
                assertEquals(item, stateResponse)
            }
        }

    private fun fakeDataSourceResponse(messages: List<Message>) {
        every { getChatMessagesUseCase(user) } returns flowOf(messages)
    }

    private fun initViewModel() {
        viewModel = ChatViewModel(
            getChatMessagesUseCase,
            sendTextMessageUseCase,
            user,
            coroutineDispatcherProvider,
        )
    }
}
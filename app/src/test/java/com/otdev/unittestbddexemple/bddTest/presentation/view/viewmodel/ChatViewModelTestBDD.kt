package com.otdev.unittestbddexemple.bddTest.presentation.view.viewmodel

import app.cash.turbine.test
import com.otdev.unittestbddexemple.core.di.appModule
import com.otdev.unittestbddexemple.data.datasource.ChatDataSource
import com.otdev.unittestbddexemple.domain.model.Message
import com.otdev.unittestbddexemple.presentation.model.MessageUiModel
import com.otdev.unittestbddexemple.presentation.view.viewmodel.ChatViewModel
import com.otdev.unittestbddexemple.utils.datasource.FakeChatDataSource
import com.otdev.unittestbddexemple.utils.di.testsModule
import com.otdev.unittestbddexemple.utils.factory.UserTestFactory
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.koin.core.parameter.parametersOf
import org.koin.java.KoinJavaComponent.get
import org.koin.java.KoinJavaComponent.inject
import java.util.Date
import kotlin.test.assertEquals

class ChatViewModelTest {

    private val modulesToLoad: List<Module> = appModule + testsModule
    private val viewModel: ChatViewModel by inject(ChatViewModel::class.java) {
        parametersOf(UserTestFactory().produce())
    }


    @Before
    fun initKoin() {
        startKoin {
            modules(modulesToLoad)
        }
    }

    @After
    fun afterTest() {
        stopKoin()
    }

    @Test
    fun `Given I have exchanged messages with a user messages user Then there are displayed`() =
        runTest {
            //Given
            fakeDataSourceResponse()
            val stateResponse = ChatViewModel.State(
                messages = listOf(
                    MessageUiModel(
                        text = "1st message",
                        type = Message.Type.Received
                    )
                )
            )

            //When

            //Then
            viewModel.state.test {
                val item = awaitItem()
                assertEquals(stateResponse, item)
            }
        }

    @Test
    fun `Given I have exchanged messages with a user messages user When I send a text message And It is valid Then It is displayed`() =
        runTest {
            //Given
            fakeDataSourceResponse()
            val textMessage = "2nd message"
            val stateResponse = ChatViewModel.State(
                messages = listOf(
                    MessageUiModel(
                        text = "1st message",
                        type = Message.Type.Received
                    ),
                    MessageUiModel(
                        text = "2nd message",
                        type = Message.Type.Received
                    )
                )
            )

            //When
            viewModel.onTextMessageSent(textMessage)

            //Then
            viewModel.state.test {
                val item = awaitItem()
                assertEquals(stateResponse, item)
            }
        }

    @Test
    fun `Given I have exchanged messages with a user messages user When I send a text message And It is not valid Then It is not displayed`() =
        runTest {
            //Given
            fakeDataSourceResponse()
            val textMessage = (1..180).joinToString("") { "a" }
            val stateResponse = ChatViewModel.State(
                messages = listOf(
                    MessageUiModel(
                        text = "1st message",
                        type = Message.Type.Received
                    ),
                )
            )

            //When
            viewModel.onTextMessageSent(textMessage)

            //Then
            viewModel.state.test {
                val item = awaitItem()
                assertEquals(stateResponse, item )
            }
        }

    private fun fakeDataSourceResponse() {
        get<FakeChatDataSource>(FakeChatDataSource::class.java).setResponse(
            listOf(Message("id", "1st message", Date(), Message.Type.Received))
        )
    }
}
package com.otdev.unittestbddexemple.core.di

import com.otdev.unittestbddexemple.data.repository.ChatMessagesRepositoryImpl
import com.otdev.unittestbddexemple.domain.repository.ChatMessagesRepository
import com.otdev.unittestbddexemple.domain.usecase.FakeUseCaseImpl
import com.otdev.unittestbddexemple.domain.usecase.GetChatMessagesUseCaseImpl
import com.otdev.unittestbddexemple.domain.usecase.SendTextMessageUseCaseImpl
import com.otdev.unittestbddexemple.domain.usecase.interfaces.FakeUseCase
import com.otdev.unittestbddexemple.domain.usecase.interfaces.GetChatMessagesUseCase
import com.otdev.unittestbddexemple.domain.usecase.interfaces.SendTextMessageUseCase
import com.otdev.unittestbddexemple.presentation.view.viewmodel.ChatViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { ChatViewModel(get(), get(), get(), get()) }

    factory<GetChatMessagesUseCase> { GetChatMessagesUseCaseImpl(get()) }
    factory<SendTextMessageUseCase> { SendTextMessageUseCaseImpl(get()) }
    factory<FakeUseCase> { FakeUseCaseImpl() }

    single<ChatMessagesRepository> { ChatMessagesRepositoryImpl(get()) }
}

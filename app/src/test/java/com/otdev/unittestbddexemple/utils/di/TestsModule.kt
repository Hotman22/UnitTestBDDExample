package com.otdev.unittestbddexemple.utils.di

import com.otdev.unittestbddexemple.core.CoroutineDispatcherProvider
import com.otdev.unittestbddexemple.core.DefaultCoroutineDispatcherProvider
import com.otdev.unittestbddexemple.data.datasource.ChatDataSource
import com.otdev.unittestbddexemple.utils.common.TestCoroutineDispatcherProvider
import com.otdev.unittestbddexemple.utils.datasource.FakeChatDataSource
import org.koin.dsl.bind
import org.koin.dsl.module


val testsModule = module {
    single { FakeChatDataSource() } bind  ChatDataSource::class
    factory<CoroutineDispatcherProvider>{ TestCoroutineDispatcherProvider() }
}

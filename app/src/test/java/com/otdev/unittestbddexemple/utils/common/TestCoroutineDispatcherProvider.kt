package com.otdev.unittestbddexemple.utils.common

import com.otdev.unittestbddexemple.core.CoroutineDispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher

class TestCoroutineDispatcherProvider : CoroutineDispatcherProvider {
    override val main: CoroutineDispatcher = TestCoroutineDispatcher()
}

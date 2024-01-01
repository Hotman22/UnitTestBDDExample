package com.otdev.unittestbddexemple.domain.usecase

import com.otdev.unittestbddexemple.domain.usecase.interfaces.FakeUseCase

class FakeUseCaseImpl: FakeUseCase {

    override fun clear(text: String): String {
        if (isValid(text)) {
            return "clearMessage"
        }
        return "NotClearMessage"
    }

    private fun isValid(text: String): Boolean {
        return text.length < 180
    }
}

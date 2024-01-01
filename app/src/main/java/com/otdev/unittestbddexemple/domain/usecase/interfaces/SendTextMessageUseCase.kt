package com.otdev.unittestbddexemple.domain.usecase.interfaces

import com.otdev.unittestbddexemple.domain.model.User

interface SendTextMessageUseCase {
    suspend operator fun invoke(text: String, user: User)
}

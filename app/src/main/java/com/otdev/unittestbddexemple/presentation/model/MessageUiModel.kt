package com.otdev.unittestbddexemple.presentation.model

import com.otdev.unittestbddexemple.domain.model.Message

data class MessageUiModel(
    val text: String,
    val type: Message.Type
)

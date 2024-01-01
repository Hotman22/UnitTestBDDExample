package com.otdev.unittestbddexemple.presentation.model.mapper

import com.otdev.unittestbddexemple.domain.model.Message
import com.otdev.unittestbddexemple.presentation.model.MessageUiModel

object MessageUiModelMapper {
    fun fromMessagesList(messagesList: List<Message>): List<MessageUiModel> {
        return messagesList.map { MessageUiModel(text = it.text, type = Message.Type.Received) }
    }
}

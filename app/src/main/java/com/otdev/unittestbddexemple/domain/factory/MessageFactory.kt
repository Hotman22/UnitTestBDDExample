package com.otdev.unittestbddexemple.domain.factory

import com.otdev.unittestbddexemple.domain.model.Message
import java.util.Date
import java.util.UUID

object MessageFactory {
    fun fromText(text: String): Message {
        return Message(
            id = UUID.randomUUID().toString(),
            text = text,
            date = Date(),
            type = Message.Type.Pending
        )
    }
}

package com.otdev.unittestbddexemple.utils.factory

import com.otdev.unittestbddexemple.domain.model.User
import java.util.UUID

class UserTestFactory(
    private val id: String = UUID.randomUUID().toString(),
    private val name: String = "test name",
) {

    fun produce(): User {
        return User(id = id, name = name)
    }
}

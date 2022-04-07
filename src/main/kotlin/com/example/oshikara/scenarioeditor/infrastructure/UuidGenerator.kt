package com.example.oshikara.scenarioeditor.infrastructure

import java.util.UUID

object UuidGenerator {
    fun random() = UUID.randomUUID().toString()
}

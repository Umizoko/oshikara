package com.example.oshikara.scenarioeditor.infrastructure

import java.util.*

object UuidGenerator {
    fun random() = UUID.randomUUID().toString()
}
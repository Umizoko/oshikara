package com.example.oshikara.scenarioeditor.infrastructure.impl.domain

import com.fasterxml.jackson.databind.ObjectMapper
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database

object TestUtil {

    private val flyway: Flyway = Flyway
        .configure()
        .dataSource("jdbc:h2:mem:oshikara", "oshikara", "oshikara")
        .load()

    private val mapper = ObjectMapper()

    fun setup() {
        flyway.migrate()
        Database.connect(
            url = "jdbc:h2:mem:oshikara",
            driver = "org.h2.Driver",
            user = "oshikara",
            password = "oshikara"
        )
    }

    fun teardown() {
        flyway.clean()
    }

    fun mapperWriteValueAsString(value: Any): String =
        mapper.writeValueAsString(value)
}

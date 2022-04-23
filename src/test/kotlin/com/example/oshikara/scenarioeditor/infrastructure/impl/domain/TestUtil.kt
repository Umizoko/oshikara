package com.example.oshikara.scenarioeditor.infrastructure.impl.domain

import com.fasterxml.jackson.databind.ObjectMapper
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database

object TestUtil {

    private val flyway: Flyway = Flyway
        .configure()
        .dataSource("jdbc:mysql://localhost:3306/oshikara", "user", "password")
        .load()

    private val mapper = ObjectMapper()

    fun setup() {
        flyway.migrate()
        Database.connect(
            url = "jdbc:mysql://localhost:3306/oshikara",
            driver = "com.mysql.cj.jdbc.Driver",
            user = "user",
            password = "password"
        )
    }

    fun teardown() {
        flyway.clean()
    }

    fun mapperWriteValueAsString(value: Any): String =
        mapper.writeValueAsString(value)
}

package com.example.oshikara.scenarioeditor.infrastructure.impl.domain

import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database

object TestUtil {

    private val flyway: Flyway = Flyway
        .configure()
        .dataSource("jdbc:mysql://host.docker.internal:3306/oshikara", "user", "password")
        .load()

    fun setup() {
        flyway.migrate()
        Database.connect(
            url = "jdbc:mysql://host.docker.internal:3306/oshikara",
            driver = "com.mysql.cj.jdbc.Driver",
            user = "user",
            password = "password"
        )
    }

    fun teardown() {
        flyway.clean()
    }
}

package com.example.oshikara.scenarioeditor.infrastructure.config

import org.flywaydb.core.Flyway
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CoreConfiguration {

    @Bean
    fun getFlyway() = Flyway
        .configure()
        .dataSource("jdbc:mysql://host.docker.internal:3306/oshikara", "user", "password")
        .load()
}

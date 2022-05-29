package com.example.oshikara.scenarioeditor.infrastructure.config

import org.jetbrains.exposed.sql.Database
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DbConfiguration {

    @Bean
    fun getDbConnection() {
        // FIXME : 接続情報は外部設定から読み込むようにする
        // FIXME : クレデンシャル情報も同様
        Database.connect(
            url = "jdbc:h2:mem:oshikara",
            driver = "org.h2.Driver",
            user = "oshikara",
            password = "oshikara"
        )
    }
}

package com.example.oshikara.scenarioeditor.infrastructure.config

import org.jetbrains.exposed.sql.Database
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DBConfiguration {

    @Bean
    fun getDBConnection() {
        // FIXME : 接続情報は外部設定から読み込むようにする
        // FIXME : クレデンシャル情報も同様
        Database.connect(
            url = "jdbc:mysql://localhost:3306/oshikara",
            driver = "com.mysql.cj.jdbc.Driver",
            user = "user",
            password = "password"
        )
    }
}

package com.example.oshikara.scenarioeditor.infrastructure.impl.domain.talent

import com.example.oshikara.scenarioeditor.domain.Talent
import com.example.oshikara.scenarioeditor.domain.TalentName
import com.example.oshikara.scenarioeditor.domain.TalentRepository
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest
internal class TalentRepositoryImplTest(
    @Autowired private val talentRepository: TalentRepository,
    @Autowired private val flyway: Flyway // FIXME DB infra直接参照させるのはいただけない
) {

    @BeforeEach
    fun setup() {
        flyway.migrate()
        Database.connect(
            url = "jdbc:mysql://host.docker.internal:3306/oshikara",
            driver = "com.mysql.jdbc.Driver",
            user = "user",
            password = "password"
        )
    }

    @AfterEach
    fun teardown() {
        flyway.clean()
    }

    @Test
    fun `タレントの作成に成功する`() {
        val talent = Talent.create(
            name = TalentName("夢見 太郎")
        )
        talentRepository.insert(talent)
    }
}

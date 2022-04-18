package com.example.oshikara.scenarioeditor.infrastructure.impl.domain.talent

import com.example.oshikara.scenarioeditor.domain.talent.Talent
import com.example.oshikara.scenarioeditor.domain.talent.TalentId
import com.example.oshikara.scenarioeditor.domain.talent.TalentName
import com.example.oshikara.scenarioeditor.domain.talent.TalentRepository
import com.example.oshikara.scenarioeditor.domain.talent.TalentStatus
import com.example.oshikara.scenarioeditor.infrastructure.impl.domain.TestUtil
import com.example.oshikara.scenarioeditor.infrastructure.model.Talents
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest
internal class TalentRepositoryImplTest(
    @Autowired private val talentRepository: TalentRepository,
) {

    @BeforeEach
    fun setup() {
        TestUtil.setup()
    }

    @AfterEach
    fun teardown() {
        TestUtil.teardown()
    }

    @Test
    fun `タレントの作成に成功する`() {
        // FIXME DBの検証に書き直す
        val talent = Talent.create(
            name = TalentName("夢見 太郎")
        )
        talentRepository.insert(talent)
    }

    @Test
    fun `insertしたタレントでtalentIdが同じ時updateで更新できる`() {
        val talentId = TalentId("b3816239-389c-4dc1-a8e8-ccc63d3bc011")
        val talentName = TalentName("tanaka taro")
        val talentStatus = TalentStatus.PRIVATE
        transaction {
            Talents.insert {
                it[id] = talentId.value
                it[name] = talentName.value
                it[status] = talentStatus.name
            }
        }
        val newTalentName = TalentName("yamada hanako")
        val newTalentStatus = TalentStatus.PUBLIC
        val newTalent = Talent.reconstruct(
            id = talentId,
            name = newTalentName,
            status = newTalentStatus
        )

        talentRepository.update(newTalent)

        val updateTalent = transaction {
            Talents.select { Talents.id eq talentId.value }.single()
        }

        assertEquals(updateTalent[Talents.id], talentId.value)
        assertEquals(updateTalent[Talents.name], newTalentName.value)
        assertEquals(updateTalent[Talents.status], newTalentStatus.name)
    }

    // TODO : talentIdが異なるときDBが更新されないことを証明するユニットテストを追加する
}

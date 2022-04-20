package com.example.oshikara.scenarioeditor.infrastructure.impl.domain.talent

import com.example.oshikara.scenarioeditor.domain.talent.TalentId
import com.example.oshikara.scenarioeditor.domain.talent.TalentName
import com.example.oshikara.scenarioeditor.domain.talent.TalentRepository
import com.example.oshikara.scenarioeditor.domain.talent.TalentStatus
import com.example.oshikara.scenarioeditor.infrastructure.datacreator.TalentTestDataCreator
import com.example.oshikara.scenarioeditor.infrastructure.factory.TestTalentFactory
import com.example.oshikara.scenarioeditor.infrastructure.impl.domain.TestUtil
import com.example.oshikara.scenarioeditor.infrastructure.model.Talents
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest
internal class TalentRepositoryImplTest(
    @Autowired private val talentRepository: TalentRepository,
    @Autowired private val talentTestDataCreator: TalentTestDataCreator
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
        val createTalent = TestTalentFactory.create()

        talentRepository.insert(createTalent)
        val insertTalent = transaction {
            Talents.select { Talents.id eq createTalent.id.value }.single()
        }

        assertEquals(createTalent.id.value, insertTalent[Talents.id])
        assertEquals(createTalent.name.value, insertTalent[Talents.name])
        assertEquals(createTalent.status.name, insertTalent[Talents.status])
    }

    @Test
    fun `insertしたタレントでtalentIdが同じ時updateで更新できる`() {
        val talent = talentTestDataCreator.create()
        val newTalent = TestTalentFactory.create(
            talentId = talent.id,
            talentName = TalentName("yamada hanako"),
            talentStatus = TalentStatus.PUBLIC
        )

        talentRepository.update(newTalent)
        val updateTalent = transaction {
            Talents.select { Talents.id eq talent.id.value }.single()
        }

        assertEquals(newTalent.id.value, updateTalent[Talents.id])
        assertEquals(newTalent.name.value, updateTalent[Talents.name])
        assertEquals(newTalent.status.name, updateTalent[Talents.status])
    }

    @Test
    fun `insertしたタレントでtalentIdが異なる時updateで更新されない`() {
        val createTalent = talentTestDataCreator.create()
        val newTalent = TestTalentFactory.create(
            talentId = TalentId("0392bb37-64af-467d-ac2e-95ecc0bc759c"),
            talentName = TalentName("yamada hanako"),
            talentStatus = TalentStatus.PUBLIC
        )

        talentRepository.update(newTalent)

        val response = transaction {
            Talents.select { Talents.id eq createTalent.id.value }.single()
        }

        assertEquals(createTalent.id.value, response[Talents.id])
        assertEquals(createTalent.name.value, response[Talents.name])
        assertEquals(createTalent.status.name, response[Talents.status])
    }

    @Test
    fun `insertしたものがfindByIdで取得できる`() {
        val createTalent = TestTalentFactory.create()

        talentRepository.insert(createTalent)
        val foundTalent = talentRepository.findById(createTalent.id)

        assertEquals(createTalent.id, foundTalent?.id)
        assertEquals(createTalent.name, foundTalent?.name)
        assertEquals(createTalent.status, foundTalent?.status)
    }

    @Test
    fun `findByIdで見つからない場合、nullを返す`() {
        val createTalent = TestTalentFactory.create()
        val foundTalentId = TalentId("b3816239-389c-4dc1-a8e8-ccc63d3bc012")

        talentRepository.insert(createTalent)
        val foundTalent = talentRepository.findById(foundTalentId)

        assertNull(foundTalent)
    }
}

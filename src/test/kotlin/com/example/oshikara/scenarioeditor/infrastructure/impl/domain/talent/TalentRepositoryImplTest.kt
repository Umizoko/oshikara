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
        val talentId = TalentId("b3816239-389c-4dc1-a8e8-ccc63d3bc011")
        val talentName = TalentName("tanaka taro")
        val talentStatus = TalentStatus.PRIVATE
        val talent = Talent.reconstruct(
            id = talentId,
            name = talentName,
            status = talentStatus
        )

        talentRepository.insert(talent)

        val insertTalent = transaction {
            Talents.select { Talents.id eq talentId.value }.single()
        }

        assertEquals(talentId.value, insertTalent[Talents.id])
        assertEquals(talentName.value, insertTalent[Talents.name])
        assertEquals(talentStatus.name, insertTalent[Talents.status])
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

        assertEquals(talentId.value, updateTalent[Talents.id])
        assertEquals(newTalentName.value, updateTalent[Talents.name])
        assertEquals(newTalentStatus.name, updateTalent[Talents.status])
    }

    @Test
    fun `insertしたタレントでtalentIdが異なる時updateで更新されない`() {
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
        val newTalentId = TalentId("0392bb37-64af-467d-ac2e-95ecc0bc759c")
        val newTalentName = TalentName("yamada hanako")
        val newTalentStatus = TalentStatus.PUBLIC
        val newTalent = Talent.reconstruct(
            id = newTalentId,
            name = newTalentName,
            status = newTalentStatus
        )

        talentRepository.update(newTalent)

        val talent = transaction {
            Talents.select { Talents.id eq talentId.value }.single()
        }

        assertEquals(talentId.value, talent[Talents.id])
        assertEquals(talentName.value, talent[Talents.name])
        assertEquals(talentStatus.name, talent[Talents.status])
    }

    @Test
    fun `insertしたものがfindByIdで取得できる`() {
        val createTalentId = TalentId("b3816239-389c-4dc1-a8e8-ccc63d3bc011")
        val createTalentName = TalentName("tanaka taro")
        val createTalentStatus = TalentStatus.PRIVATE
        val createTalent = Talent.reconstruct(
            id = createTalentId,
            name = createTalentName,
            status = createTalentStatus
        )

        talentRepository.insert(createTalent)
        val foundTalent = talentRepository.findById(createTalentId)

        assertEquals(createTalent.id, foundTalent?.id)
        assertEquals(createTalent.name, foundTalent?.name)
        assertEquals(createTalent.status, foundTalent?.status)
    }
}

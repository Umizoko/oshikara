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
        // FIXME : テストデータ作成用objectの書き換え
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
        val newTalent = TestTalentFactory.create(
            talentId = talentId,
            talentName = TalentName("yamada hanako"),
            talentStatus = TalentStatus.PUBLIC
        )

        talentRepository.update(newTalent)
        val updateTalent = transaction {
            Talents.select { Talents.id eq talentId.value }.single()
        }

        assertEquals(newTalent.id.value, updateTalent[Talents.id])
        assertEquals(newTalent.name.value, updateTalent[Talents.name])
        assertEquals(newTalent.status.name, updateTalent[Talents.status])
    }

    @Test
    fun `insertしたタレントでtalentIdが異なる時updateで更新されない`() {
        // FIXME テストデータ作成用のオブジェクトに書き換え
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
        val newTalent = TestTalentFactory.create(
            talentId = TalentId("0392bb37-64af-467d-ac2e-95ecc0bc759c"),
            talentName = TalentName("yamada hanako"),
            talentStatus = TalentStatus.PUBLIC
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

object TestTalentFactory {
    fun create(
        talentId: TalentId = TalentId("b3816239-389c-4dc1-a8e8-ccc63d3bc011"),
        talentName: TalentName = TalentName("tanaka taro"),
        talentStatus: TalentStatus = TalentStatus.PRIVATE
    ) = Talent.reconstruct(
        id = talentId,
        name = talentName,
        status = talentStatus
    )
}

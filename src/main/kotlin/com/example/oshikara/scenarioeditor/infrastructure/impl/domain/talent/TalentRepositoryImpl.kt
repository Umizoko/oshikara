package com.example.oshikara.scenarioeditor.infrastructure.impl.domain.talent

import com.example.oshikara.scenarioeditor.domain.talent.Talent
import com.example.oshikara.scenarioeditor.domain.talent.TalentId
import com.example.oshikara.scenarioeditor.domain.talent.TalentName
import com.example.oshikara.scenarioeditor.domain.talent.TalentRepository
import com.example.oshikara.scenarioeditor.domain.talent.TalentStatus
import com.example.oshikara.scenarioeditor.infrastructure.model.Talents
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import org.springframework.stereotype.Component

@Component
class TalentRepositoryImpl() : TalentRepository {
    override fun insert(talent: Talent) {
        transaction {
            Talents.insert {
                it[id] = talent.id.value
                it[name] = talent.name.value
                it[status] = talent.status.name
            }
        }
    }

    override fun update(talent: Talent) {
        transaction {
            Talents.update({ Talents.id eq talent.id.value }) {
                it[name] = talent.name.value
                it[status] = talent.status.name
            }
        }
    }

    override fun findById(talentId: TalentId): Talent? {
        val talent = transaction {
            Talents.select { Talents.id eq talentId.value }
                .singleOrNull()
        } ?: return null

        return Talent.reconstruct(
            id = TalentId(talent[Talents.id]),
            name = TalentName(talent[Talents.name]),
            status = TalentStatus.valueOf(talent[Talents.status])
        )
    }
}

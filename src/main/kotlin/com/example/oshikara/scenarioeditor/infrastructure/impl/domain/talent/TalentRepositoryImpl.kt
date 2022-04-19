package com.example.oshikara.scenarioeditor.infrastructure.impl.domain.talent

import com.example.oshikara.scenarioeditor.domain.talent.Talent
import com.example.oshikara.scenarioeditor.domain.talent.TalentRepository
import com.example.oshikara.scenarioeditor.infrastructure.model.Talents
import org.jetbrains.exposed.sql.insert
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
}

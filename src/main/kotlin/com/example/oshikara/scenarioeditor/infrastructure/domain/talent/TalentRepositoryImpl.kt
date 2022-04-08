package com.example.oshikara.scenarioeditor.infrastructure.domain.talent

import com.example.oshikara.scenarioeditor.domain.Talent
import com.example.oshikara.scenarioeditor.domain.TalentRepository

class TalentRepositoryImpl() : TalentRepository {
    override fun insert(talent: Talent) {
        // TODO タレントモデル（or mapper）からDBへのinsert
    }
}

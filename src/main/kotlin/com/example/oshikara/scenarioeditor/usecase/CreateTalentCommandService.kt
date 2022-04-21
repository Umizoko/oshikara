package com.example.oshikara.scenarioeditor.usecase

import com.example.oshikara.scenarioeditor.domain.talent.Talent
import com.example.oshikara.scenarioeditor.domain.talent.TalentName
import com.example.oshikara.scenarioeditor.domain.talent.TalentRepository
import org.springframework.transaction.annotation.Transactional

class CreateTalentCommandService(
    private val talentRepository: TalentRepository
) {
    @Transactional
    fun execute(talentName: String) {
        val talent = Talent.create(TalentName(talentName))
        talentRepository.insert(talent)
    }
}

package com.example.oshikara.scenarioeditor.infrastructure.datacreator

import com.example.oshikara.scenarioeditor.domain.talent.Talent
import com.example.oshikara.scenarioeditor.domain.talent.TalentRepository
import com.example.oshikara.scenarioeditor.infrastructure.factory.TestTalentFactory
import org.springframework.stereotype.Component

@Component
class TalentTestDataCreator(
    private val talentRepository: TalentRepository
) {
    fun create(): Talent {
        val talent = TestTalentFactory.create()
        talentRepository.insert(talent)
        return talent
    }
}

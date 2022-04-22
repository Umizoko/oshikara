package com.example.oshikara.scenarioeditor.application.talent

import com.example.oshikara.scenarioeditor.domain.talent.Talent
import com.example.oshikara.scenarioeditor.domain.talent.TalentId
import com.example.oshikara.scenarioeditor.domain.talent.TalentRepository

data class FetchTalentDto(
    val talentId: String,
    val talentName: String,
    val talentStatus: String
) {
    constructor(talent: Talent) : this(
        talentId = talent.id.value,
        talentName = talent.name.value,
        talentStatus = talent.status.name
    )
}

class FetchTalentUseCase(
    private val talentRepository: TalentRepository
) {
    fun execute(talentId: TalentId): FetchTalentDto? {
        val talent = talentRepository.findById(talentId)
            ?: return null
        return FetchTalentDto(talent)
    }
}

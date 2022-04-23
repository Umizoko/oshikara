package com.example.oshikara.scenarioeditor.application.talent

import com.example.oshikara.scenarioeditor.domain.talent.Talent
import com.example.oshikara.scenarioeditor.domain.talent.TalentName
import com.example.oshikara.scenarioeditor.domain.talent.TalentRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

data class CreateTalentUseCaseDto(
    val talentId: String
)

@Component
class CreateTalentUseCase(
    private val talentRepository: TalentRepository
) {
    @Transactional
    fun execute(talentName: String): CreateTalentUseCaseDto {
        val talent = Talent.create(TalentName(talentName))
        talentRepository.insert(talent)

        return CreateTalentUseCaseDto(talent.id.value)
    }
}

package com.example.oshikara.scenarioeditor.application.talent

import com.example.oshikara.scenarioeditor.domain.talent.TalentId
import com.example.oshikara.scenarioeditor.domain.talent.TalentName
import com.example.oshikara.scenarioeditor.domain.talent.TalentRepository

data class UpdateTalentUseCaseCommand(
    val talentId: TalentId,
    val talentName: TalentName?
)

class UpdateTalentUseCase(
    private val talentRepository: TalentRepository
) {
    fun execute(command: UpdateTalentUseCaseCommand) {
        val talent = talentRepository.findById(command.talentId)
            ?: throw Exception("not exist talent")

        if (command.talentName != null) {
            talent.changeName(command.talentName)
        }

        talentRepository.update(talent)
    }
}

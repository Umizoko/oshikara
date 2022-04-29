package com.example.oshikara.scenarioeditor.application.talent

import com.example.oshikara.scenarioeditor.domain.talent.TalentId
import com.example.oshikara.scenarioeditor.domain.talent.TalentName
import com.example.oshikara.scenarioeditor.domain.talent.TalentRepository

sealed class UpdateTalentUseCaseException(message: String) : Exception(message) {
    class NotFoundTalentException(message: String) : UpdateTalentUseCaseException(message)
}

data class UpdateTalentUseCaseCommand(
    val talentId: TalentId,
    val talentName: TalentName?
)

class UpdateTalentUseCase(
    private val talentRepository: TalentRepository
) {
    fun execute(command: UpdateTalentUseCaseCommand) {
        val talent = talentRepository.findById(command.talentId)
            ?: throw UpdateTalentUseCaseException
                .NotFoundTalentException("Not found talent resource. talentId: ${command.talentId.value}")

        if (command.talentName != null) {
            talent.changeName(command.talentName)
        }

        talentRepository.update(talent)
    }
}

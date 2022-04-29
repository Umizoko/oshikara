package com.example.oshikara.scenarioeditor.application.talent

import com.example.oshikara.scenarioeditor.domain.talent.TalentId
import com.example.oshikara.scenarioeditor.domain.talent.TalentName
import com.example.oshikara.scenarioeditor.domain.talent.TalentRepository
import com.example.oshikara.scenarioeditor.domain.talent.TalentStatus

sealed class UpdateTalentUseCaseException(message: String) : Exception(message) {
    class NotFoundTalentException(message: String) : UpdateTalentUseCaseException(message)
}

data class UpdateTalentUseCaseDpo(
    val talentId: TalentId,
    val talentName: TalentName?,
    val talentStatus: TalentStatus?
)

class UpdateTalentUseCase(
    private val talentRepository: TalentRepository
) {
    fun execute(command: UpdateTalentUseCaseDpo) {
        val talent = talentRepository.findById(command.talentId)
            ?: throw UpdateTalentUseCaseException
                .NotFoundTalentException("Not found talent resource. talentId: ${command.talentId.value}")

        command.talentName?.let { talent.changeName(it) }

        command.talentStatus?.let {
            when (it) {
                TalentStatus.PUBLIC -> talent.public()
                TalentStatus.PRIVATE -> talent.private()
            }
        }

        talentRepository.update(talent)
    }
}

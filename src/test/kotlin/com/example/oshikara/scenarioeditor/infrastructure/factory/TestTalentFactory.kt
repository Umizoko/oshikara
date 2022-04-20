package com.example.oshikara.scenarioeditor.infrastructure.factory

import com.example.oshikara.scenarioeditor.domain.talent.Talent
import com.example.oshikara.scenarioeditor.domain.talent.TalentId
import com.example.oshikara.scenarioeditor.domain.talent.TalentName
import com.example.oshikara.scenarioeditor.domain.talent.TalentStatus

object TestTalentFactory {
    fun create(
        talentId: TalentId = TalentId("b3816239-389c-4dc1-a8e8-ccc63d3bc011"),
        talentName: TalentName = TalentName("tanaka taro"),
        talentStatus: TalentStatus = TalentStatus.PRIVATE
    ) = Talent.reconstruct(
        id = talentId,
        name = talentName,
        status = talentStatus
    )
}

package com.example.oshikara.scenarioeditor.application.talent

import com.example.oshikara.scenarioeditor.domain.talent.Talent
import com.example.oshikara.scenarioeditor.domain.talent.TalentId
import com.example.oshikara.scenarioeditor.domain.talent.TalentName
import com.example.oshikara.scenarioeditor.domain.talent.TalentRepository
import com.example.oshikara.scenarioeditor.infrastructure.factory.TestTalentFactory
import io.mockk.CapturingSlot
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class UpdateTalentUseCaseTest {

    private val talentRepository: TalentRepository = mockk()

    private val updateTalentUseCase = UpdateTalentUseCase(talentRepository)

    @Test
    fun `タレントIDと変更後の名前を渡すと、名前の値が更新されたタレントが保存される`() {
        val talent = TestTalentFactory.create()
        val talentCapturingSlog: CapturingSlot<Talent> = slot()
        every { talentRepository.findById(talent.id) } returns talent
        every { talentRepository.update(capture(talentCapturingSlog)) } just Runs

        val command = UpdateTalentUseCaseCommand(
            talentId = TalentId(talent.id.value),
            talentName = TalentName("name after update")
        )
        updateTalentUseCase.execute(command)

        val capturedTalent: Talent = talentCapturingSlog.captured

        assertEquals(command.talentId, capturedTalent.id)
        assertEquals(command.talentName, capturedTalent.name)
    }
}

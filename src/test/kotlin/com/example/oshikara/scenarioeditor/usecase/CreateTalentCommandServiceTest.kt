package com.example.oshikara.scenarioeditor.usecase

import com.example.oshikara.scenarioeditor.domain.talent.Talent
import com.example.oshikara.scenarioeditor.domain.talent.TalentName
import com.example.oshikara.scenarioeditor.domain.talent.TalentRepository
import com.example.oshikara.scenarioeditor.domain.talent.TalentStatus
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
internal class CreateTalentCommandServiceTest {

    private val talentRepository: TalentRepository = mockk()

    private val createTalentCommandService = CreateTalentCommandService(talentRepository)

    @Test
    fun `タレント名を渡すと、その値を使用して新規作成されたタレントが保存される`() {
        val talentCapturingSlot: CapturingSlot<Talent> = slot()
        every { talentRepository.insert(capture(talentCapturingSlot)) } just Runs

        val talentName = "test taro"
        createTalentCommandService.execute(talentName)

        val capturedTalent: Talent = talentCapturingSlot.captured

        assertEquals(TalentName(talentName), capturedTalent.name)
        assertEquals(TalentStatus.PRIVATE, capturedTalent.status)
    }
}

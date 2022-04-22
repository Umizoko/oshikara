package com.example.oshikara.scenarioeditor.application.talent

import com.example.oshikara.scenarioeditor.domain.talent.TalentId
import com.example.oshikara.scenarioeditor.domain.talent.TalentRepository
import com.example.oshikara.scenarioeditor.infrastructure.factory.TestTalentFactory
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class FetchTalentUseCaseTest {

    private val talentRepository: TalentRepository = mockk()

    private val useCase = FetchTalentUseCase(talentRepository)

    @Test
    fun `タレントリポジトリから値が取得できる場合、結果がDTOに詰め替えて返される`() {
        val talent = TestTalentFactory.create()
        every { talentRepository.findById(talent.id) } returns talent

        val actualDto = useCase.execute(talent.id)

        val expectDto = FetchTalentDto(
            talentId = talent.id.value,
            talentName = talent.name.value,
            talentStatus = talent.status.name
        )

        assertEquals(expectDto, actualDto)
    }

    @Test
    fun `タレントリポジトリから返される値がnullの場合、nullが返される`() {
        val talentId = TalentId()
        every { talentRepository.findById(talentId) } returns null

        val actualDto = useCase.execute(talentId)

        assertNull(actualDto)
    }
}

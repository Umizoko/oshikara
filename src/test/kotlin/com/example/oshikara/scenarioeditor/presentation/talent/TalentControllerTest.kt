package com.example.oshikara.scenarioeditor.presentation.talent

import com.example.oshikara.scenarioeditor.application.talent.CreateTalentUseCase
import com.example.oshikara.scenarioeditor.application.talent.CreateTalentUseCaseDto
import com.example.oshikara.scenarioeditor.application.talent.FetchTalentUseCase
import com.example.oshikara.scenarioeditor.application.talent.FetchTalentUseCaseDto
import com.example.oshikara.scenarioeditor.infrastructure.factory.TestTalentFactory
import com.example.oshikara.scenarioeditor.infrastructure.impl.domain.TestUtil
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TalentControllerTest {
    private lateinit var mockMvc: MockMvc

    private val createTalentUseCase: CreateTalentUseCase = mockk()

    private val fetchTalentUseCase: FetchTalentUseCase = mockk()

    private val talentController = TalentController(createTalentUseCase, fetchTalentUseCase)

    @BeforeEach
    fun setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(talentController).build()
    }

    @Test
    fun `talentsにpostして成功した場合、201でlocationにリソースのURIが設定されている`() {
        val talentId = "aed091f7-970a-4d45-9692-f7b12667c310"
        val talentName = "yamada taro"
        every { createTalentUseCase.execute(talentName) } returns CreateTalentUseCaseDto(talentId)

        mockMvc.perform(
            MockMvcRequestBuilders
                .post("/talents")
                .content(TestUtil.mapperWriteValueAsString(CreateTalentRequest(talentName)))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated)
            .andExpect(header().string("Location", "http://localhost:8080/talents/$talentId"))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
    }

    @Test
    fun `タレントIDでgetして成功した場合、200でタレントID、名前、ステータスを返す`() {
        val talent = TestTalentFactory.create()
        every { fetchTalentUseCase.execute(talent.id) } returns FetchTalentUseCaseDto(talent)

        val response = mockMvc.perform(
            MockMvcRequestBuilders
                .get("/talents/${talent.id.value}")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn()
            .response

        assertEquals(
            "{\"id\":\"b3816239-389c-4dc1-a8e8-ccc63d3bc011\",\"name\":\"tanaka taro\",\"status\":\"PRIVATE\"}",
            response.contentAsString
        )
    }
}

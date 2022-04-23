package com.example.oshikara.scenarioeditor.presentation.talent

import com.example.oshikara.scenarioeditor.application.talent.CreateTalentUseCase
import com.example.oshikara.scenarioeditor.application.talent.CreateTalentUseCaseDto
import com.fasterxml.jackson.databind.ObjectMapper
import io.mockk.every
import io.mockk.mockk
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

    private val mapper = ObjectMapper()

    private val createTalentUseCase: CreateTalentUseCase = mockk()

    private val targetController = TalentController(createTalentUseCase)

    @BeforeEach
    fun setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(targetController).build()
    }

    @Test
    fun `talentsにpostして成功した場合、201でlocationにリソースのURIが設定されている`() {
        val talentId = "aed091f7-970a-4d45-9692-f7b12667c310"
        val talentName = "yamada taro"
        every { createTalentUseCase.execute(talentName) } returns CreateTalentUseCaseDto(talentId)

        mockMvc.perform(
            MockMvcRequestBuilders
                .post("/talents")
                .content(mapper.writeValueAsString(RequestCreateTalent(talentName)))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated)
            .andExpect(header().string("Location", "http://localhost:8080/talents/$talentId"))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
    }
}

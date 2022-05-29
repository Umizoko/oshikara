package com.example.oshikara.scenarioeditor.infrastructure.config

import com.example.oshikara.scenarioeditor.application.talent.CreateTalentUseCase
import com.example.oshikara.scenarioeditor.application.talent.CreateTalentUseCaseDto
import com.example.oshikara.scenarioeditor.application.talent.FetchTalentUseCase
import com.example.oshikara.scenarioeditor.infrastructure.impl.domain.TestUtil
import com.example.oshikara.scenarioeditor.presentation.talent.CreateTalentRequest
import com.example.oshikara.scenarioeditor.presentation.talent.TalentController
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders

internal class WebConfigurationTest {

    private lateinit var mockMvc: MockMvc

    private val createTalentUseCase: CreateTalentUseCase = mockk()

    private val fetchTalentUseCase: FetchTalentUseCase = mockk()

    private val talentController = TalentController(createTalentUseCase, fetchTalentUseCase)

    @BeforeEach
    fun setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(talentController).build()
    }

    @Test
    fun `レスポンスヘッダーにAccess-Control-Allow-Originにstoplightが含まれている`() {
        val talentId = "aed091f7-970a-4d45-9692-f7b12667c310"
        val talentName = "yamada taro"
        every { createTalentUseCase.execute(talentName) } returns CreateTalentUseCaseDto(talentId)

        mockMvc.perform(
            MockMvcRequestBuilders
                .post("/talents")
                .content(TestUtil.mapperWriteValueAsString(CreateTalentRequest(talentName)))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.header().string("Access-Control-Allow-Origin", "https://umizoko.stoplight.io"))
    }
}
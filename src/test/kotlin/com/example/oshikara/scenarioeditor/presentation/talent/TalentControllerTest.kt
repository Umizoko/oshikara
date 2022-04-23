package com.example.oshikara.scenarioeditor.presentation.talent

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class TalentControllerTest(
    @Autowired private val mockMvc: MockMvc
) {

    private val mapper = ObjectMapper()

    @Test
    fun `talentsにpostして成功した場合、201でlocationにリソースのURIが設定されている`() {
        mockMvc.perform (
                MockMvcRequestBuilders
                    .post("/talents")
                    .content(mapper.writeValueAsString(RequestCreateTalent("aaa")))
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isCreated)
            .andExpect(header().string("Location", "http://localhost:8080/talents/xxxxx-xxxx-xxxxx-xxxxx"))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
    }
}

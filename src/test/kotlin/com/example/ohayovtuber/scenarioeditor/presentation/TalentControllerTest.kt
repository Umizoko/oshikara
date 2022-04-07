package com.example.ohayovtuber.scenarioeditor.presentation

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.http.HttpStatus

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TalentControllerTests(
    @Autowired private val template: TestRestTemplate
) {

    @Test
    fun `リクエストに成功する`() {
        val entity = template.getForEntity<String>("/")
        assertEquals(HttpStatus.OK, entity.statusCode)
        assertEquals("Greetings from Spring Boot!", entity.body)
    }
}
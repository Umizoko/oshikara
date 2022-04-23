package com.example.oshikara.scenarioeditor.presentation.talent

import com.example.oshikara.scenarioeditor.application.talent.CreateTalentUseCase
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.net.URI

data class RequestCreateTalent(
    val name: String
)

@RestController
class TalentController(
    private val createTalentUseCase: CreateTalentUseCase
) {

    @PostMapping("/talents")
    fun create(@RequestBody talent: RequestCreateTalent): ResponseEntity<String> {
        val createTalentUseCaseDto = createTalentUseCase.execute(talent.name)
        return ResponseEntity
            .created(URI.create("http://localhost:8080").resolve("/talents/${createTalentUseCaseDto.talentId}"))
            .contentType(MediaType.APPLICATION_JSON)
            .build()
    }
}

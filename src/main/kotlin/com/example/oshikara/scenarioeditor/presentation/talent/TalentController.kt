package com.example.oshikara.scenarioeditor.presentation.talent

import com.example.oshikara.scenarioeditor.application.talent.CreateTalentUseCase
import com.example.oshikara.scenarioeditor.application.talent.FetchTalentUseCase
import com.example.oshikara.scenarioeditor.domain.talent.TalentId
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.net.URI

data class RequestCreateTalent(
    val name: String
)

data class GetTalentResponse(
    private val id: String,
    private val name: String,
    private val status: String
)

@RestController
class TalentController(
    private val createTalentUseCase: CreateTalentUseCase,
    private val fetchTalentUseCase: FetchTalentUseCase
) {

    @PostMapping("/talents")
    fun create(@RequestBody talent: RequestCreateTalent): ResponseEntity<String> {
        val createTalentUseCaseDto = createTalentUseCase.execute(talent.name)
        return ResponseEntity
            .created(URI.create("http://localhost:8080").resolve("/talents/${createTalentUseCaseDto.talentId}"))
            .contentType(MediaType.APPLICATION_JSON)
            .build()
    }

    @GetMapping("/talents/{talentId}")
    fun get(@PathVariable("talentId") talentId: String): ResponseEntity<GetTalentResponse> {
        val fetchTalentUseCaseDto = fetchTalentUseCase.execute(TalentId(talentId))
            ?: return ResponseEntity.notFound().build()

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                GetTalentResponse(
                    id = fetchTalentUseCaseDto.talentId,
                    name = fetchTalentUseCaseDto.talentName,
                    status = fetchTalentUseCaseDto.talentStatus
                )
            )
    }
}

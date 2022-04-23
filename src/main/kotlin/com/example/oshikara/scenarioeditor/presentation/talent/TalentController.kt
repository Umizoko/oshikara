package com.example.oshikara.scenarioeditor.presentation.talent

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
class TalentController {

    @PostMapping("/talents")
    fun create(@RequestBody talent: RequestCreateTalent): ResponseEntity<String> {
        return ResponseEntity
            .created(URI.create("http://localhost:8080").resolve("/talents/xxxxx-xxxx-xxxxx-xxxxx"))
            .contentType(MediaType.APPLICATION_JSON)
            .build()
    }
}

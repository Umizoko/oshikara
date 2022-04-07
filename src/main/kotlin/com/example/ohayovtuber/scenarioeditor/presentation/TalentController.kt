package com.example.ohayovtuber.scenarioeditor.presentation

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TalentController {

    @GetMapping("/")
    fun index() : String = "Greetings from Spring Boot!"
}
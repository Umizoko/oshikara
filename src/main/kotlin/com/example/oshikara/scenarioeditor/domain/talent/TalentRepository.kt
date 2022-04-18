package com.example.oshikara.scenarioeditor.domain.talent

interface TalentRepository {
    fun insert(talent: Talent)

    fun update(talent: Talent)
}

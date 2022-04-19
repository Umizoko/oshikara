package com.example.oshikara.scenarioeditor.domain.talent

import com.example.oshikara.scenarioeditor.infrastructure.UuidGenerator

data class TalentId(val value: String) {
    constructor() : this(UuidGenerator.random())
}

enum class TalentStatus {

    PRIVATE,

    PUBLIC
}

class Talent private constructor(
    id: TalentId,
    name: TalentName,
    status: TalentStatus
) {
    val id = id

    var name = name
        private set
    var status = status
        private set

    companion object {
        fun create(name: TalentName): Talent =
            Talent(
                id = TalentId(),
                name = name,
                status = TalentStatus.PRIVATE
            )

        fun reconstruct(
            id: TalentId,
            name: TalentName,
            status: TalentStatus
        ): Talent {
            return Talent(
                id = id,
                name = name,
                status = status
            )
        }
    }

    fun public() {
        this.status = TalentStatus.PUBLIC
    }

    fun private() {
        this.status = TalentStatus.PRIVATE
    }

    fun changeName(name: TalentName) {
        this.name = name
    }
}

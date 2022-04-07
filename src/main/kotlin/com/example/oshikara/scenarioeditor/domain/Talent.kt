package com.example.oshikara.scenarioeditor.domain

import com.example.oshikara.scenarioeditor.infrastructure.UuidGenerator

// FIXME Format

data class TalentId(val value : String) {
    constructor() : this(UuidGenerator.random())
}

data class TalentName(val value: String) {
    init {
        // TODO ヴァリデーションを追加
    }
}

enum class TalentStatus{

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
        fun create(name: TalentName) : Talent =
            Talent(
                id = TalentId(),
                name = name,
                status = TalentStatus.PRIVATE
            )
    }

    fun public() {
        this.status = TalentStatus.PUBLIC
    }

    fun private() {
        this.status = TalentStatus.PRIVATE
    }
}

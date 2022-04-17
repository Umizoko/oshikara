package com.example.oshikara.scenarioeditor.domain.talent

class IllegalTalentNameException(override val message: String) : Throwable()

data class TalentName(val value: String) {
    init {
        if (value.length > TALENT_NAME_MAX_LENGTH) {
            throw IllegalTalentNameException("名前は255文字以下で入力してください")
        }
    }

    companion object {
        private const val TALENT_NAME_MAX_LENGTH = 255
    }
}

package com.example.oshikara.scenarioeditor.domain.talent

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class TalentNameTest {

    @Test
    fun `255文字以下だと正常にインスタンスが作成される`() {
        val name = "a".repeat(255)

        val talentName = TalentName(name)

        assertEquals(name, talentName.value)
    }

    @Test
    fun `256文字を超えてると例外が発生する`() {
        val illegalName = "a".repeat(256)

        val target: () -> Unit = {
            TalentName(illegalName)
        }

        val exception = assertThrows<IllegalTalentNameException>(target)
        assertEquals("名前は255文字以下で入力してください", exception.message)
    }
}

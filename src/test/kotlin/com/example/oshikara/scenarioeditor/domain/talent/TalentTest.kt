package com.example.oshikara.scenarioeditor.domain.talent

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class TalentTest {

    @Test
    fun `新しくタレントを作成すると、非公開のインスタンスが生成される`() {
        val name = TalentName("山田ナターシア 46Billion☆")
        val status = TalentStatus.PRIVATE
        val talent = Talent.create(name)

        assertEquals(status, talent.status)
        assertEquals(name, talent.name)
    }

    @Test
    fun `タレントを公開にすると、インスタンスのステータスが公開になる`() {
        val name = TalentName("山田ナターシア 46Billion☆")
        val talent = Talent.create(name)

        talent.public()

        assertEquals(TalentStatus.PUBLIC, talent.status)
    }

    @Test
    fun `タレントを非公開にすると、インスタンスのステータスが非公開になる`() {
        val name = TalentName("山田ナターシア 46Billion☆")
        val talent = Talent.create(name)

        talent.private()

        assertEquals(TalentStatus.PRIVATE, talent.status)
    }

    @Test
    fun `タレントの名前を変更すると、インスタンスの名前が変更後のものになる`() {
        val nameBeforeChangeName = TalentName("夢見 太郎")
        val talent = Talent.create(nameBeforeChangeName)
        val newName = TalentName("変換 ゴタロウ")

        talent.changeName(newName)

        assertEquals(newName, talent.name)
    }
}

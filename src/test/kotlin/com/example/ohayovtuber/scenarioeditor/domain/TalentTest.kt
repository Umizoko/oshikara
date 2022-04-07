package com.example.ohayovtuber.scenarioeditor.domain

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class TalentTest {

    @Test
    fun `新しくタレントを作成すると、非公開のインスタンスが生成される`(){
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
}
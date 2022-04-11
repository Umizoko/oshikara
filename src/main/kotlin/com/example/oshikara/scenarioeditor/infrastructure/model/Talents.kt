package com.example.oshikara.scenarioeditor.infrastructure.model

import org.jetbrains.exposed.sql.Table

object Talents : Table("talents") {
    val id = varchar("id", 36)
    val name = varchar("name", 255)
    val status = varchar("status", 32)

    override val primaryKey = PrimaryKey(id, name = "PK_Talent_ID")
}

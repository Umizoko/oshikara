package com.example.oshikara.scenarioeditor.infrastructure.model

import org.jetbrains.exposed.sql.Table

object Talents : Table("talents") {

    private const val ID_LENGTH = 36
    private const val NAME_LENGTH = 255
    private const val STATUS_LENGTH = 32

    val id = varchar("id", ID_LENGTH)
    val name = varchar("name", NAME_LENGTH)
    val status = varchar("status", STATUS_LENGTH)

    override val primaryKey = PrimaryKey(id, name = "PK_Talent_ID")
}

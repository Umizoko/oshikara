package com.example.oshikara.scenarioeditor.infrastructure.impl.domain.talent

import com.example.oshikara.scenarioeditor.domain.talent.Talent
import com.example.oshikara.scenarioeditor.domain.talent.TalentName
import com.example.oshikara.scenarioeditor.domain.talent.TalentRepository
import com.example.oshikara.scenarioeditor.infrastructure.impl.domain.TestUtil
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest
internal class TalentRepositoryImplTest(
    @Autowired private val talentRepository: TalentRepository,
) {

    @BeforeEach
    fun setup() {
        TestUtil.setup()
    }

    @AfterEach
    fun teardown() {
        TestUtil.teardown()
    }

    @Test
    fun `タレントの作成に成功する`() {
        val talent = Talent.create(
            name = TalentName("夢見 太郎")
        )
        talentRepository.insert(talent)
    }

//    @Test
//    fun `タレントの名前の変更に成功する`() {
//        val talent = Talent.create(
//            name = TalentName("夢見 太郎")
//        )
//        talentRepository.insert(talent)
//
//        talent.changeName("変換 誤太郎")
//        val changeNameTalent = talentRepository.update(talent)
//
//    }
}

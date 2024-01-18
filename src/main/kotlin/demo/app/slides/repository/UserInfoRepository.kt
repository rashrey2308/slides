package demo.app.slides.repository

import demo.app.slides.common.domain.entities.UserInfo
import org.springframework.data.jpa.repository.JpaRepository

interface UserInfoRepository : JpaRepository<UserInfo, String> {

    fun findByEmailId(emailId: String): UserInfo?
}


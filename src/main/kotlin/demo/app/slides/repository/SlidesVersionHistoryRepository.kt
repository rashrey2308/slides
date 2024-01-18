package demo.app.slides.repository

import demo.app.slides.common.domain.entities.SlidesVersionHistory
import demo.app.slides.common.domain.entities.UserInfo
import org.springframework.data.jpa.repository.JpaRepository

interface SlidesVersionHistoryRepository : JpaRepository<SlidesVersionHistory, String> {
    fun findByPresentationId(presentationId: String): SlidesVersionHistory?

}


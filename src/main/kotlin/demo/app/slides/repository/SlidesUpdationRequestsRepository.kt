package demo.app.slides.repository

import demo.app.slides.common.domain.entities.SlideUpdationRequests
import org.springframework.data.jpa.repository.JpaRepository
import java.sql.Timestamp

interface SlidesUpdationRequestsRepository : JpaRepository<SlideUpdationRequests, String> {

//    fun findAllBySlidesVersionHistoryIdAndGreaterThanUpdatedAt(id: Int, updatedAt: Timestamp): List<SlideUpdationRequests>

    fun findAllBySlidesVersionHistoryId(id: Int): List<SlideUpdationRequests>
}

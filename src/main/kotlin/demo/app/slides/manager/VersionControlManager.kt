package demo.app.slides.manager

import demo.app.slides.common.SlidesEndpoints
import demo.app.slides.common.domain.entities.SlideUpdationRequests
import demo.app.slides.common.domain.entities.SlidesVersionHistory
import demo.app.slides.common.models.AutoSaveRequestsList
import demo.app.slides.repository.SlidesUpdationRequestsRepository
import demo.app.slides.repository.SlidesVersionHistoryRepository
import org.springframework.stereotype.Component
import java.sql.Timestamp
import java.time.Instant

@Component
class VersionControlManager(
    private val slidesVersionHistoryRepository: SlidesVersionHistoryRepository,
    private val slidesUpdationRequestsRepository: SlidesUpdationRequestsRepository
) {
    fun autosave(presentationId: String, autoSaveRequestsList: AutoSaveRequestsList) {
        val now = Timestamp.from(Instant.now())
        val slidesVersionHistoryInfo = slidesVersionHistoryRepository.findByPresentationId(presentationId)
            ?: slidesVersionHistoryRepository.save(
                SlidesVersionHistory(
                    presentationId = presentationId,
                    lastUpdatedAt = now
                )
            )
        autoSaveRequestsList.requests.forEach {
            slidesUpdationRequestsRepository.save(
                SlideUpdationRequests(
                    slidesVersionHistoryInfo.id!!,
                    it.kind,
                    it.requestJson,
                    now
                )
            )
        }
    }
}

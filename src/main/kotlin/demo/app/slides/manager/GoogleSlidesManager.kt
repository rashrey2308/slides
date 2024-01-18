package demo.app.slides.manager

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.util.JSONPObject
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.google.api.services.slides.v1.model.BatchUpdatePresentationRequest
import com.google.api.services.slides.v1.model.BatchUpdatePresentationResponse
import com.google.api.services.slides.v1.model.CreateSlideRequest
import com.google.api.services.slides.v1.model.Presentation
import com.google.api.services.slides.v1.model.Request
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import demo.app.slides.helpers.GoogleAuthHelper
import demo.app.slides.repository.SlidesUpdationRequestsRepository
import demo.app.slides.repository.SlidesVersionHistoryRepository
import org.springframework.stereotype.Component
import java.sql.Timestamp
import java.time.Instant

@Component
class GoogleSlidesManager(
    private val googleAuthHelper: GoogleAuthHelper,
    private val slidesVersionHistoryRepository: SlidesVersionHistoryRepository,
    private val slidesUpdationRequestsRepository: SlidesUpdationRequestsRepository
) {

    private val objectMapper = jacksonObjectMapper().apply {
        this.registerKotlinModule()
        this.registerModule(JavaTimeModule())
        this.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
    }

    fun getPresentation(presentationId: String): Presentation? {
        val presentation = googleAuthHelper.googleSlidesClient()
            .presentations()
            .get(presentationId)
            .execute()
        return presentation
    }

    fun createPresentation(): Presentation? {
        return googleAuthHelper.googleSlidesClient()
            .presentations()
            .create(Presentation())
            .execute()
    }

    // Move to version control manager or something?
    // Explore revisionId sent by google?
    fun batchUpdate(presentationId: String): BatchUpdatePresentationResponse? {
        // if there's no entry then nothing was autosaved and nothing is there for us to update
        val slidesVersionHistoryInfo = slidesVersionHistoryRepository.findByPresentationId(presentationId) ?: return null
        val lastUpdateTime = slidesVersionHistoryInfo.lastUpdationTimeAtGoogle
        val now = Timestamp.from(Instant.now())
        val requests = if (lastUpdateTime == null) {
            slidesUpdationRequestsRepository.findAllBySlidesVersionHistoryId(slidesVersionHistoryInfo.id!!)
        } else {

            // We are assuming that after every n seconds, FE will send us a request to autosave with a list of update
            // requests. This will also be saved to their local cache. These requests will be incremental update requests,
            // in the sense that if an update request r1 was sent after T+n seconds, then after T+2n seconds if the
            // update diff from google's version to local version is (r1, r2), it will only send us r2 and not both r1, r2.
            // The pro would be less data transfer. The con would be that in case of connection lost there are chances of
            // updates missed at BE.

            slidesUpdationRequestsRepository.findAllBySlidesVersionHistoryId(
                slidesVersionHistoryInfo.id!!
            ).filter {
                it.updatedAt > lastUpdateTime && it.updatedAt <= now
            }
        }

        val requestList = mutableListOf<Request>()

        requests.forEach {
            requestList.add(Request().set(
                it.kind.googleRequestKey,
                objectMapper.readValue(it.requestJson, it.kind.requestClassType::class.java)
            ))
        }

        return if (requestList.isNotEmpty()) {
            val response = googleAuthHelper.googleSlidesClient()
                .presentations()
                .batchUpdate(
                    presentationId,
                    BatchUpdatePresentationRequest().setRequests(requestList)
                )
                .execute()
            slidesVersionHistoryRepository.save(
                slidesVersionHistoryInfo.copy(lastUpdationTimeAtGoogle = now)
            )
            response
        } else null
    }
}

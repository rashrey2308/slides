package demo.app.slides.service

import com.google.api.services.drive.model.FileList
import com.google.api.services.slides.v1.model.BatchUpdatePresentationResponse
import com.google.api.services.slides.v1.model.Presentation
import demo.app.slides.manager.GoogleDriveManager
import demo.app.slides.manager.GoogleSlidesManager
import org.springframework.stereotype.Service

@Service
class GoogleSlidesService(
    private val googleSlidesManager: GoogleSlidesManager
) {
    fun getPresentation(presentationId: String): Presentation? {
        return googleSlidesManager.getPresentation(presentationId)
    }

    fun createPresentation(): Presentation? {
        return googleSlidesManager.createPresentation()
    }

    fun batchUpdate(presentationId: String): BatchUpdatePresentationResponse? {
        return googleSlidesManager.batchUpdate(presentationId)
    }
}

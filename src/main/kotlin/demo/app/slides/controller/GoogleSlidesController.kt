package demo.app.slides.controller

import com.google.api.services.drive.model.CommentList
import com.google.api.services.slides.v1.model.BatchUpdatePresentationResponse
import com.google.api.services.slides.v1.model.Presentation
import demo.app.slides.common.models.AutoSaveRequestsList
import demo.app.slides.service.GoogleSlidesService
import demo.app.slides.service.VersionControlService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class GoogleSlidesController(
    private val googleSlidesService: GoogleSlidesService,
    private val versionControlService: VersionControlService
) {
    @GetMapping("/slides/getPresentation")
    fun getGoogleOAuthUrl(
        @RequestParam("presentationId") presentationId: String
    ): Presentation? {
        return googleSlidesService.getPresentation(presentationId)
    }

    @GetMapping("/slides/createPresentation")
    fun getGoogleOAuthUrl(): String? {
        return googleSlidesService.createPresentation()?.toPrettyString()
    }

    @PostMapping("/slides/batchUpdatePresentation")
    fun getAllComments(
        @RequestParam("presentationId") presentationId: String
    ): BatchUpdatePresentationResponse? {
        return googleSlidesService.batchUpdate(presentationId)
    }

    @PostMapping("/slides/autoSave")
    fun autoSave(
        @RequestParam("presentationId") presentationId: String,
        @RequestBody autoSaveRequestsList: AutoSaveRequestsList
    ) {
        versionControlService.autosave(presentationId, autoSaveRequestsList)
    }
}

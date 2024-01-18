package demo.app.slides.controller

import com.google.api.services.drive.model.CommentList
import demo.app.slides.service.GoogleDriveService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class GoogleDriveController(
    private val googleDriveService: GoogleDriveService,
) {
    // Directly defining the url string here in interest of time
    @GetMapping("/gdrive/slides/findAll")
    fun getAllSlides(): String? {
        return googleDriveService.listAllSlides()?.toPrettyString()
    }

    @GetMapping("/gdrive/comments/findAll")
    fun getAllComments(
        @RequestParam("fileId") fileId: String
    ): CommentList? {
        return googleDriveService.getAllComments(fileId)
    }
}

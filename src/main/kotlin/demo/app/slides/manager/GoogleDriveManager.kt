package demo.app.slides.manager

import com.google.api.services.drive.model.CommentList
import com.google.api.services.drive.model.FileList
import demo.app.slides.helpers.GoogleAuthHelper
import org.springframework.stereotype.Component

@Component
class GoogleDriveManager(
    private val googleAuthHelper: GoogleAuthHelper,
) {
    fun listAllSlides(): FileList? {
        val slides = googleAuthHelper.googleDriveClient()
            .files()
            .list()
            .setQ("mimeType = 'application/vnd.google-apps.presentation'")
            .setFields("*")
            .execute()
        return slides
    }

    // Do we really need this?
    fun getAllComments(fileId: String): CommentList? {
        val comments = googleAuthHelper.googleDriveClient()
            .comments()
            .list(fileId)
            .execute()
        return comments
    }
}

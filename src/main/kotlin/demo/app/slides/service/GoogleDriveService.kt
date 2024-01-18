package demo.app.slides.service

import com.google.api.services.drive.model.CommentList
import com.google.api.services.drive.model.DriveList
import com.google.api.services.drive.model.FileList
import demo.app.slides.manager.GoogleDriveManager
import org.springframework.stereotype.Service

/**
 * Ideally should manage all the dto conversions and exception handling, metrics, etc
 * But in the interest of time, this is skipped
 */
@Service
class GoogleDriveService(
    private val googleDriveManager: GoogleDriveManager
) {
    fun listAllSlides(): FileList? {
        return googleDriveManager.listAllSlides()
    }

    fun getAllComments(fileId: String): CommentList? {
        return googleDriveManager.getAllComments(fileId)
    }
}

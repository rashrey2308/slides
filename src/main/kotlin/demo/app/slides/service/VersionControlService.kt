package demo.app.slides.service

import demo.app.slides.common.models.AutoSaveRequestsList
import demo.app.slides.manager.VersionControlManager
import org.springframework.stereotype.Service

@Service
class VersionControlService(
    private val versionControlManager: VersionControlManager
) {

    fun autosave(presentationId: String, autoSaveRequestsList: AutoSaveRequestsList) {
        versionControlManager.autosave(presentationId, autoSaveRequestsList)
    }

}

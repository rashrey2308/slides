package demo.app.slides.common.models

import demo.app.slides.common.domain.entities.SlideUpdationRequests

data class AutoSaveRequest(
    val kind: SlideUpdationRequests.Kind,
    val requestJson: String
)

data class AutoSaveRequestsList(
    val requests: List<AutoSaveRequest>
)

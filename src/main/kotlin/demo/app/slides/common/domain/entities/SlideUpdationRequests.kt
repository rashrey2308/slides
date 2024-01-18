package demo.app.slides.common.domain.entities

import com.google.api.client.json.GenericJson
import com.google.api.services.slides.v1.model.CreateShapeRequest
import com.google.api.services.slides.v1.model.CreateSlideRequest
import com.google.api.services.slides.v1.model.InsertTextRequest
import com.google.api.services.slides.v1.model.Request
import jakarta.persistence.*
import java.sql.Timestamp
import java.time.Instant

@Entity
@Table(name = "slide_updation_requests")
data class SlideUpdationRequests (
    val slidesVersionHistoryId: Int,

    @Enumerated(EnumType.STRING)
    val kind: Kind,

    val requestJson: String,

    val updatedAt: Timestamp,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null

) {
    enum class Kind(val googleRequestKey: String, val requestClassType: GenericJson) {
        // Limiting to 3 types in interest of time
        CREATE_SLIDE("createSlide", CreateSlideRequest()),
        CREATE_SHAPE("createShape", CreateShapeRequest()),
        INSERT_TEXT("insertText", InsertTextRequest())
    }
}

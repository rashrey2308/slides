package demo.app.slides.common.domain.entities

import jakarta.persistence.*
import java.sql.Timestamp
import java.time.Instant

@Entity
@Table(name = "slides_version_history")
data class SlidesVersionHistory (
    val presentationId: String,

    val lastUpdatedAt: Timestamp,

    val lastUpdationTimeAtGoogle: Timestamp? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null

) {}

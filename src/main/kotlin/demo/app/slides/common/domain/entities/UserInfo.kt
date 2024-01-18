package demo.app.slides.common.domain.entities

import jakarta.persistence.*


@Entity
@Table(name="user_info")
data class UserInfo(
    val userId: String,

    val fullName: String,

    // Make it unique in db and add index
    val emailId: String,

    val imageUrl: String,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
)

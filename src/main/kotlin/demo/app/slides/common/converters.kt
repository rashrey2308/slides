package demo.app.slides.common

import com.google.api.services.oauth2.model.Userinfo
import demo.app.slides.common.domain.entities.UserInfo

fun Userinfo.toDomain() = UserInfo(
    userId = this.id,
    emailId = this.email,
    fullName = this.name,
    imageUrl = this.picture
)

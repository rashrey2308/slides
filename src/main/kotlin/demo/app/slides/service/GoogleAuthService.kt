package demo.app.slides.service

import demo.app.slides.common.GoogleAuth
import demo.app.slides.common.domain.entities.UserInfo
import demo.app.slides.common.toDomain
import demo.app.slides.controller.GoogleAuthController
import demo.app.slides.helpers.GoogleAuthHelper
import demo.app.slides.repository.UserInfoRepository
import org.springframework.stereotype.Service

@Service
class GoogleAuthService(
    private val googleAuthHelper: GoogleAuthHelper,
    private val userInfoRepository: UserInfoRepository,
) {
    fun getGoogleOAuthUrl(redirectUri: String): String? {
        return googleAuthHelper.googleAuthorizationCodeFlow().newAuthorizationUrl()
            .setRedirectUri(redirectUri)
            .build()
    }

    fun handleCallback(authorizationCode: String): String {
//        val oauth2Client = googleAuthHelper.getOauth2Client(authorizationCode)
//        val userInfo = oauth2Client.userinfo().get().execute()
//        return userInfoRepository.save(userInfo.toDomain()).fullName + " " + oauth2Client
        return "Hello"
    }
}

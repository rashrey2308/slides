package demo.app.slides.controller

import com.google.api.services.oauth2.model.Userinfo
import demo.app.slides.common.GoogleAuth
import demo.app.slides.common.SlidesEndpoints
import demo.app.slides.helpers.GoogleAuthHelper
import demo.app.slides.service.GoogleAuthService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class GoogleAuthController(
    private val googleAuthService: GoogleAuthService,
    private val googleAuthHelper: GoogleAuthHelper
   ) {
    private val redirectUri = "http://localhost:8080/"+SlidesEndpoints.Auth.GoogleAuthRedirectCallback

    @GetMapping(SlidesEndpoints.Auth.GoogleOAuthUrl)
    fun getGoogleOAuthUrl(): String? {
        return googleAuthService.getGoogleOAuthUrl(redirectUri)
    }

    @GetMapping(SlidesEndpoints.Auth.GoogleAuthRedirectCallback)
    fun handleGoogleAuthRedirectCallback(
        @RequestParam("code") authorizationCode: String
    ): String {
        return "Hello " + googleAuthService.handleCallback(authorizationCode)
    }
}

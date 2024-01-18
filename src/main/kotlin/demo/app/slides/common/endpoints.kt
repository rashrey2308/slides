package demo.app.slides.common

object SlidesEndpoints {

    const val Base = "slides/v1/"

    object Auth {
        const val GoogleOAuthUrl = Base + "googleOauth/getUri"
        const val GoogleAuthRedirectCallback = Base + "googleOAuth/callback"
    }
}

object GoogleAuth {
    const val redirectUri = "http://localhost:8080/"+SlidesEndpoints.Auth.GoogleAuthRedirectCallback
}

package demo.app.slides.helpers

import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.HttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.gson.GsonFactory
import com.google.api.client.util.store.FileDataStoreFactory
import com.google.api.services.drive.Drive
import com.google.api.services.drive.DriveScopes
import com.google.api.services.oauth2.Oauth2
import com.google.api.services.oauth2.Oauth2Scopes
import com.google.api.services.slides.v1.Slides
import com.google.api.services.slides.v1.SlidesScopes
import com.google.api.services.slides.v1.model.Page
import com.google.api.services.slides.v1.model.Presentation
import demo.app.slides.common.GoogleAuth
import demo.app.slides.helpers.GoogleAuthHelper.Companion.HTTP_TRANSPORT
import org.springframework.stereotype.Component
import java.io.*
import java.security.GeneralSecurityException


@Component
class GoogleAuthHelper {
    /**
     * Creates an authorized Credential object.
     *
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    fun googleAuthorizationCodeFlow(): GoogleAuthorizationCodeFlow {
        // Build flow to trigger user authorization request / request token.
        return GoogleAuthorizationCodeFlow.Builder(
            HTTP_TRANSPORT, JSON_FACTORY, getClientSecrets(), SCOPES
        )
            .setDataStoreFactory(
                FileDataStoreFactory(
                    File(TOKENS_DIRECTORY_PATH)
                )
            )
            .setAccessType("offline")
            .build()
    }

    fun getOauth2Client(authorizationCode: String): Oauth2 {
        val credential: Credential = GoogleCredential.Builder()
            .setTransport(HTTP_TRANSPORT)
            .setJsonFactory(JSON_FACTORY)
            .setClientSecrets(getClientSecrets())
            .build()
            .setFromTokenResponse(
                googleAuthorizationCodeFlow().newTokenRequest(authorizationCode)
                    .setRedirectUri(GoogleAuth.redirectUri)
                    .execute()
            )

        return Oauth2
            .Builder(
                HTTP_TRANSPORT,
                JSON_FACTORY,
                credential
            )
            .setApplicationName(APPLICATION_NAME)
            .build()
    }

    fun googleDriveClient(): Drive {
        return Drive
            .Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredential())
            .setApplicationName(APPLICATION_NAME)
            .build()
    }

    fun googleSlidesClient(): Slides {
        return Slides
            .Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredential())
            .setApplicationName(APPLICATION_NAME)
            .build()
    }

    private fun getCredential(): Credential {
        return AuthorizationCodeInstalledApp(googleAuthorizationCodeFlow(), RECEIVER)
            .authorize("user")
    }

    // Load Client Secrets
    private fun getClientSecrets(): GoogleClientSecrets {
        val `in`: InputStream = this::class.java.getResourceAsStream(CREDENTIALS_FILE_PATH)
            ?: throw FileNotFoundException("Resource not found: $CREDENTIALS_FILE_PATH")
        val clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, InputStreamReader(`in`))
        return clientSecrets

    }

    // Todo: Move to config
    companion object {
        private const val APPLICATION_NAME = "GSlides"
        private const val TOKENS_DIRECTORY_PATH = "tokens"
        val JSON_FACTORY: JsonFactory = GsonFactory.getDefaultInstance()
        val HTTP_TRANSPORT: HttpTransport = GoogleNetHttpTransport.newTrustedTransport()

        /**
         * Global instance of the scopes required by this quickstart.
         * If modifying these scopes, delete your previously saved tokens/ folder.
         */
        private val SCOPES: List<String> = listOf(SlidesScopes.PRESENTATIONS, DriveScopes.DRIVE, Oauth2Scopes.USERINFO_PROFILE, Oauth2Scopes.USERINFO_EMAIL)
        private const val CREDENTIALS_FILE_PATH = "/credentials.json"

        val RECEIVER: LocalServerReceiver = LocalServerReceiver.Builder()
            .setPort(2424)
            .build()

    }
}

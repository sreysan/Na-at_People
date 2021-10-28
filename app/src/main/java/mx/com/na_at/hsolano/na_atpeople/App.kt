package mx.com.na_at.hsolano.na_atpeople

import android.app.Application
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import mx.com.na_at.hsolano.na_atpeople.util.Constants
import mx.com.na_at.hsolano.na_atpeople.util.Constants.KEY_URL_PHOTO
import mx.com.na_at.hsolano.na_atpeople.util.Constants.SHARED_PREFERENCES_NAME
import mx.com.na_at.hsolano.na_atpeople.util.Constants.SP_REFRESH_TOKEN_KEY
import mx.com.na_at.hsolano.na_atpeople.util.Constants.SP_TOKEN_KEY

class App : Application() {

    companion object {
        private lateinit var instance: App
        private lateinit var googleSignInClient: GoogleSignInClient

        private val preferences by lazy {
            instance.getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE)
        }

        fun getToken() = preferences.getString(SP_TOKEN_KEY, "") ?: ""
        fun getRefreshToken() = preferences.getString(SP_REFRESH_TOKEN_KEY, "") ?: ""
        fun getPhotoUrl() = preferences.getString(KEY_URL_PHOTO, "") ?: ""

        fun saveToken(token: String) {
            preferences.edit().putString(SP_TOKEN_KEY, token).apply()
        }

        fun saveRefresh(refreshToken: String) {
            preferences.edit().putString(SP_REFRESH_TOKEN_KEY, refreshToken).apply()
        }

        fun savePhotoUrl(url: String) {
            preferences.edit().putString(KEY_URL_PHOTO, url).apply()
        }


        fun removeUserInfo() {
            preferences.edit().remove(SP_TOKEN_KEY).commit()
            preferences.edit().remove(SP_REFRESH_TOKEN_KEY).commit()
            preferences.edit().remove(KEY_URL_PHOTO).commit()
        }

        fun getGoogleSignInClient() = googleSignInClient
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        //Google Auth
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(Constants.AUTH_API_ID_KEY)
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)

    }

}
package mx.com.na_at.hsolano.na_atpeople.view.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import mx.com.na_at.hsolano.na_atpeople.App
import mx.com.na_at.hsolano.na_atpeople.R
import mx.com.na_at.hsolano.na_atpeople.model.repository.LoginRepository
import mx.com.na_at.hsolano.na_atpeople.util.Constants.AUTH_API_ID_KEY
import mx.com.na_at.hsolano.na_atpeople.util.Constants.KEY_URL_PHOTO
import mx.com.na_at.hsolano.na_atpeople.view.fragment.dialogs.LoginDialogFragment
import mx.com.na_at.hsolano.na_atpeople.viewmodel.LoginViewModel
import mx.com.na_at.hsolano.na_atpeople.viewmodel.LoginViewModelFactory

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private var photoUrl = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val buttonLogin = findViewById<ConstraintLayout>(R.id.constraintLayoutButtonLogin)
        buttonLogin.setOnClickListener {
            signIn()
        }

        val repository = LoginRepository()
        loginViewModel = ViewModelProvider(this, LoginViewModelFactory(repository)).get(
            LoginViewModel::class.java
        )

        loginViewModel.photoUrl.observe(this, { url ->
            App.savePhotoUrl(url)
            photoUrl = url
        })

        loginViewModel.isEmailValid.observe(this, { isValid ->
            if (!isValid)
                showEmailErrorDialog()
        })

        // LOGIN SUCCESSFUL PARAMS
        loginViewModel.token.observe(this, { token ->
            App.saveToken(token)
        })
        loginViewModel.refreshToken.observe(this, { refreshToken ->
            App.saveRefresh(refreshToken)
        })

        loginViewModel.isLoginSuccessful.observe(this, {
            if (it)
                goToHomeActivity()
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)

                account.email?.let { email ->
                    account.idToken?.let { idToken ->
                        account.photoUrl?.let { url ->
                            photoUrl = url.toString()
                            loginViewModel.validIfEmailIsValid(email, idToken, photoUrl)
                        }
                    }
                }

            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.e(TAG, "Google sign in failed", e)
            }
        }
    }

    private fun signIn() {
        val signInIntent = App.getGoogleSignInClient().signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun signOut() {
        App.getGoogleSignInClient().signOut()
            .addOnCompleteListener(this) {
            }
    }

    private fun goToHomeActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra(KEY_URL_PHOTO, photoUrl)
        startActivity(intent)
        finish()
    }

    private fun showEmailErrorDialog() {
        val createDialog = LoginDialogFragment()
        createDialog.show(supportFragmentManager, "Alert")
        signOut()
    }


    companion object {
        private const val TAG = "GoogleActivity"
        private const val RC_SIGN_IN = 9001
    }
}
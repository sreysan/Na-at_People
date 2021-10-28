package mx.com.na_at.hsolano.na_atpeople.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mx.com.na_at.hsolano.na_atpeople.model.network.request.LoginRequest
import mx.com.na_at.hsolano.na_atpeople.model.network.response.LoginResponse
import mx.com.na_at.hsolano.na_atpeople.model.repository.LoginRepository
import mx.com.na_at.hsolano.na_atpeople.view.contract.LoginCallback

class LoginViewModel(private val repository: LoginRepository) : ViewModel() {

    var isLoginSuccessful = MutableLiveData<Boolean>()
    var token = MutableLiveData<String>()
    var refreshToken = MutableLiveData<String>()


    var isEmailValid = MutableLiveData<Boolean>()
    var photoUrl = MutableLiveData<String>()

    private fun requestDoLogin(authToken: String) {
        val loginRequest = LoginRequest(authToken)
        repository.requestLogin(loginRequest, object : LoginCallback {
            override fun onResponseSuccessLogin(response: LoginResponse) {
                token.postValue(response.accessToken)
                refreshToken.postValue(response.refreshToken)
                isLoginSuccessful.postValue(true)
            }

            // TODO: VALIDATE LOGIN UNSUCCESSFUL
            override fun onResponseFailureLogin(throwable: Throwable) {
                isLoginSuccessful.postValue(false)
            }

        })
    }

    fun validIfEmailIsValid(email: String, googleToken: String, url: String) {
        val isValidEmail = email.contains("@na-at.com.mx")
        if (isValidEmail) {
            requestDoLogin(googleToken)
            photoUrl.value = url
        }
        isEmailValid.value = isValidEmail
    }

}
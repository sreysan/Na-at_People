package mx.com.na_at.hsolano.na_atpeople.view.contract

import mx.com.na_at.hsolano.na_atpeople.model.network.response.LoginResponse

interface LoginCallback {
    fun onResponseSuccessLogin(response: LoginResponse)
    fun onResponseFailureLogin(throwable: Throwable)
}

class LoginError : Throwable()


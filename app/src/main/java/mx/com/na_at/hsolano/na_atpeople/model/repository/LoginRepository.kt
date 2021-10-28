package mx.com.na_at.hsolano.na_atpeople.model.repository

import mx.com.na_at.hsolano.na_atpeople.model.network.request.LoginRequest
import mx.com.na_at.hsolano.na_atpeople.model.network.response.LoginResponse
import mx.com.na_at.hsolano.na_atpeople.model.network.retrofit.ApiClient
import mx.com.na_at.hsolano.na_atpeople.util.Constants.DEVELOP_SERVER
import mx.com.na_at.hsolano.na_atpeople.view.contract.LoginCallback
import mx.com.na_at.hsolano.na_atpeople.view.contract.LoginError
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginRepository {

    fun requestLogin(requestLogin: LoginRequest, callback: LoginCallback) {
        val result: Call<LoginResponse> =
            ApiClient.buildApiService(DEVELOP_SERVER)
                .doLogIn(requestLogin)
        result.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val access = response.body() as LoginResponse
                    callback.onResponseSuccessLogin(access)
                } else {
                    callback.onResponseFailureLogin(LoginError())
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                callback.onResponseFailureLogin(t)
            }

        })
    }
}
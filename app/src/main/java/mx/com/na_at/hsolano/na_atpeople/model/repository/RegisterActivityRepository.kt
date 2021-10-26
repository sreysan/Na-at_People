package mx.com.na_at.hsolano.na_atpeople.model.repository

import androidx.lifecycle.MutableLiveData
import mx.com.na_at.hsolano.na_atpeople.model.ActivityHour
import mx.com.na_at.hsolano.na_atpeople.model.network.response.RegisterObjectResponse
import mx.com.na_at.hsolano.na_atpeople.model.network.retrofit.ApiClient
import mx.com.na_at.hsolano.na_atpeople.util.Constants.DEVELOP_SERVER
import mx.com.na_at.hsolano.na_atpeople.view.contract.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivityRepository {

    fun getAllClients(listener: GetRegisterActivityListener) {
        val result: Call<List<RegisterObjectResponse>> =
            ApiClient.buildApiService(DEVELOP_SERVER).getAllClients()

        result.enqueue(object : Callback<List<RegisterObjectResponse>> {
            override fun onResponse(
                call: Call<List<RegisterObjectResponse>>,
                response: Response<List<RegisterObjectResponse>>
            ) {
                if (response.isSuccessful) {
                    val list = response.body() as List<RegisterObjectResponse>
                    listener.onGetClientsSuccess(list)
                } else {
                    listener.onGetClientsError(GetClientsError())
                }
            }

            override fun onFailure(call: Call<List<RegisterObjectResponse>>, t: Throwable) {
                listener.onGetClientsError(t)
            }
        })

    }

    fun getProjectsByClientId(id: String, callback: GetProjectsByClientIdCallback) {
        val result: Call<List<RegisterObjectResponse>> =
            ApiClient.buildApiService("https://demo7683580.mockable.io/").getProjectsByIdClient(id)

        result.enqueue(object : Callback<List<RegisterObjectResponse>> {
            override fun onResponse(
                call: Call<List<RegisterObjectResponse>>,
                response: Response<List<RegisterObjectResponse>>
            ) {
                if (response.isSuccessful) {
                    val list = response.body() as List<RegisterObjectResponse>
                    callback.onGetProjectsByClientIdSuccessful(list)
                } else {
                    callback.onGetProjectsByClientIdFailure(GetProjectsByIdError())
                }
            }

            override fun onFailure(call: Call<List<RegisterObjectResponse>>, error: Throwable) {
                callback.onGetProjectsByClientIdFailure(error)
            }
        })
    }

    fun getAllActivities(callback: GetActivitiesCallback) {
        val result: Call<List<RegisterObjectResponse>> =
            ApiClient.buildApiService(DEVELOP_SERVER).getAllActivities()

        result.enqueue(object : Callback<List<RegisterObjectResponse>> {
            override fun onResponse(
                call: Call<List<RegisterObjectResponse>>,
                response: Response<List<RegisterObjectResponse>>
            ) {
                if (response.isSuccessful) {
                    val list = response.body() as List<RegisterObjectResponse>
                    callback.onGetActivitiesSuccessful(list)
                } else
                    callback.onGetActivitiesFailure(GetActivitiesError())
            }

            override fun onFailure(call: Call<List<RegisterObjectResponse>>, t: Throwable) {
                callback.onGetActivitiesFailure(t)
            }
        })
    }

}
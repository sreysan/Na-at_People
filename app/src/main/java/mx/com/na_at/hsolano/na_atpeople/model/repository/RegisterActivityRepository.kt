package mx.com.na_at.hsolano.na_atpeople.model.repository

import androidx.lifecycle.MutableLiveData
import mx.com.na_at.hsolano.na_atpeople.model.network.response.RegisterObjectResponse
import mx.com.na_at.hsolano.na_atpeople.model.network.retrofit.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivityRepository {

    fun getAllClients(): MutableLiveData<List<Pair<String, String>>> {
        val clients = MutableLiveData<List<Pair<String, String>>>()

        val result: Call<List<RegisterObjectResponse>> =
            ApiClient.buildApiService("http://3.238.21.227:8080/").getAllClients()

        result.enqueue(object : Callback<List<RegisterObjectResponse>> {
            override fun onResponse(
                call: Call<List<RegisterObjectResponse>>,
                response: Response<List<RegisterObjectResponse>>
            ) {
                if (response.isSuccessful) {
                    val list = response.body() as List<RegisterObjectResponse>

                    val clientsPair = mutableListOf<Pair<String, String>>()
                    for (client in list) {
                        clientsPair.add(Pair(client.id, client.name))
                    }
                    clients.value = clientsPair
                }
            }

            override fun onFailure(call: Call<List<RegisterObjectResponse>>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })

        return clients

    }

    fun getProjectsByClientId(id: String): MutableLiveData<List<Pair<String, String>>> {
        val clients = MutableLiveData<List<Pair<String, String>>>()
        val result: Call<List<RegisterObjectResponse>> =
            ApiClient.buildApiService("https://demo7683580.mockable.io/").getProjectsByIdClient(id)

        result.enqueue(object : Callback<List<RegisterObjectResponse>> {
            override fun onResponse(
                call: Call<List<RegisterObjectResponse>>,
                response: Response<List<RegisterObjectResponse>>
            ) {
                if (response.isSuccessful) {
                    val list = response.body() as List<RegisterObjectResponse>

                    val clientsPair = mutableListOf<Pair<String, String>>()
                    for (client in list) {
                        clientsPair.add(Pair(client.id, client.name))
                    }
                    clients.value = clientsPair
                }
            }

            override fun onFailure(call: Call<List<RegisterObjectResponse>>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })

        return clients

    }
}
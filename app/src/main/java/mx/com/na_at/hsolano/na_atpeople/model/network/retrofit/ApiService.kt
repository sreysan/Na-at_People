package mx.com.na_at.hsolano.na_atpeople.model.network.retrofit

import mx.com.na_at.hsolano.na_atpeople.model.News
import mx.com.na_at.hsolano.na_atpeople.model.NewsDetail
import mx.com.na_at.hsolano.na_atpeople.model.network.response.RegisterObjectResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("news")
    fun getAllNews(): Call<List<News>>

    @GET("newsDetail/{id}")
    fun getNewsDetail(@Path("id") id: String): Call<NewsDetail>

    @GET("clients")
    fun getAllClients(): Call<List<RegisterObjectResponse>>

    @GET("clients/{clientId}/projects/")
    fun getProjectsByIdClient(@Path("clientId") id: String): Call<List<RegisterObjectResponse>>
}
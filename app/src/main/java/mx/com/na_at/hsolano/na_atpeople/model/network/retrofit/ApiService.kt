package mx.com.na_at.hsolano.na_atpeople.model.network.retrofit

import mx.com.na_at.hsolano.na_atpeople.model.News
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("news")
    fun getAllNews(): Call<List<News>>
}
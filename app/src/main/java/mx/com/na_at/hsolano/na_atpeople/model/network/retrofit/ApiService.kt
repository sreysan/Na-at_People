package mx.com.na_at.hsolano.na_atpeople.model.network.retrofit

import mx.com.na_at.hsolano.na_atpeople.model.News
import mx.com.na_at.hsolano.na_atpeople.model.NewsDetail
import mx.com.na_at.hsolano.na_atpeople.model.network.request.LoginRequest
import mx.com.na_at.hsolano.na_atpeople.model.network.request.UpdateActivityRecordsRequest
import mx.com.na_at.hsolano.na_atpeople.model.network.response.ActivityRecordResponse
import mx.com.na_at.hsolano.na_atpeople.model.network.response.LoginResponse
import mx.com.na_at.hsolano.na_atpeople.model.network.response.RegisterObjectResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("news")
    fun getAllNews(): Call<List<News>>

    @GET("news/{id}")
    fun getNewsDetail(@Path("id") id: String): Call<NewsDetail>

    @GET("clients")
    fun getAllClients(): Call<List<RegisterObjectResponse>>

    @GET("clients/{clientId}/projects/")
    fun getProjectsByIdClient(@Path("clientId") id: String): Call<List<RegisterObjectResponse>>

    @GET("activities")
    fun getAllActivities(): Call<List<RegisterObjectResponse>>

    @GET("activity-records")
    fun getActivityRecords(): Call<List<ActivityRecordResponse>>

    @POST("auth/login")
    fun doLogIn(@Body auth: LoginRequest): Call<LoginResponse>

    @DELETE("/activity-records/{id}")
    fun deleteActivityRecords(@Path("id") id: String): Call<Unit>

    @PUT("/activity-records/{id}")
    fun updateActivityRecords(@Path("id") id: String, @Body body: UpdateActivityRecordsRequest)
            : Call<Unit>


}
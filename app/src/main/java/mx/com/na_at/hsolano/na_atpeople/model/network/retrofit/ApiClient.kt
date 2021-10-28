package mx.com.na_at.hsolano.na_atpeople.model.network.retrofit

import mx.com.na_at.hsolano.na_atpeople.App
import mx.com.na_at.hsolano.na_atpeople.util.Constants.HEADER_AUTHORIZATION
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {

    companion object {
        private fun getRetrofitClient(urlBase: String): Retrofit {
            return Retrofit.Builder()
                .baseUrl(urlBase)
                .addConverterFactory(GsonConverterFactory.create())
                .client(buildHttpClient()).build()
        }

        private fun buildHttpClient(): OkHttpClient {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            return OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(buildAuthorizationInterceptor()).build()
        }

        fun buildApiService(urlBase: String): ApiService =
            getRetrofitClient(urlBase).create(ApiService::class.java)

        private fun buildAuthorizationInterceptor() = object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val originalRequest = chain.request()
                if (App.getToken().isBlank()) return chain.proceed(originalRequest)
                val new =
                    originalRequest.newBuilder().addHeader(HEADER_AUTHORIZATION, App.getToken())
                        .build()
                return chain.proceed(new)
            }
        }

    }
}
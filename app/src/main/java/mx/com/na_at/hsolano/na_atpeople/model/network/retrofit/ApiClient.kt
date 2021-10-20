package mx.com.na_at.hsolano.na_atpeople.model.network.retrofit

import okhttp3.OkHttpClient
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
                .addInterceptor(interceptor).build()
        }

        fun buildApiService(urlBase: String): ApiService =
            getRetrofitClient(urlBase).create(ApiService::class.java)

    }
}
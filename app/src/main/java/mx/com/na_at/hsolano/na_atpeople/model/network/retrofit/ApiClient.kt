package mx.com.na_at.hsolano.na_atpeople.model.network.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {

    companion object {
        fun getRetrofitClient(): Retrofit {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder()
                .addInterceptor(interceptor).build()

            val retrofit = Retrofit.Builder()
                .baseUrl("https://demo6074034.mockable.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)

            return retrofit.build()
        }
    }
}
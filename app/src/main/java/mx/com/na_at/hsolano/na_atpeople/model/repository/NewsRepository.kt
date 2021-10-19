package mx.com.na_at.hsolano.na_atpeople.model.repository

import androidx.lifecycle.MutableLiveData
import mx.com.na_at.hsolano.na_atpeople.model.News
import mx.com.na_at.hsolano.na_atpeople.model.network.retrofit.ApiClient
import mx.com.na_at.hsolano.na_atpeople.model.network.retrofit.ApiService
import mx.com.na_at.hsolano.na_atpeople.util.DateUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsRepository {

    val allNews = MutableLiveData<List<News>>()

    fun requestGetAllNotes(): MutableLiveData<List<News>> {
        val apiService = ApiClient.getRetrofitClient().create(ApiService::class.java)
        val result: Call<List<News>> = apiService.getAllNews()

        result.enqueue(object : Callback<List<News>> {
            override fun onResponse(call: Call<List<News>>, response: Response<List<News>>) {
                if (response.isSuccessful) {
                    response.body()?.let { news ->
                        for (post in news) {
                            post.publishDate = DateUtils.formatDate(post.publishDate)
                        }
                        allNews.value = news
                    }
                }
            }

            override fun onFailure(call: Call<List<News>>, t: Throwable) {
            }
        })
        return allNews
    }


}
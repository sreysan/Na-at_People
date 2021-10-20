package mx.com.na_at.hsolano.na_atpeople.model.repository

import androidx.lifecycle.MutableLiveData
import mx.com.na_at.hsolano.na_atpeople.model.News
import mx.com.na_at.hsolano.na_atpeople.model.NewsDetail
import mx.com.na_at.hsolano.na_atpeople.model.network.retrofit.ApiClient
import mx.com.na_at.hsolano.na_atpeople.util.DateUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsRepository {


    fun requestGetAllNews(): MutableLiveData<List<News>> {
        val allNews = MutableLiveData<List<News>>()

        val result: Call<List<News>> =
            ApiClient.buildApiService("http://demo7569783.mockable.io/").getAllNews()

        result.enqueue(object : Callback<List<News>> {
            override fun onResponse(call: Call<List<News>>, response: Response<List<News>>) {
                if (response.isSuccessful) {
                    response.body()?.let { news ->
                        for (post in news) {
                            post.publishDate = DateUtils.formatUtcDate(post.publishDate)
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

    fun requestNewsDetail(id: String): MutableLiveData<NewsDetail> {
        val newsDetail = MutableLiveData<NewsDetail>()

        val result: Call<NewsDetail> =
            ApiClient.buildApiService("https://demo6074034.mockable.io/").getNewsDetail(id)

        result.enqueue(object : Callback<NewsDetail> {
            override fun onResponse(
                call: Call<NewsDetail>,
                response: Response<NewsDetail>
            ) {
                if (response.isSuccessful) {
                    val newsDetailResponse = response.body()
                    newsDetailResponse?.let {
                        newsDetailResponse.publishDate = DateUtils.formatDateTime(newsDetailResponse.publishDate)
                    }
                    newsDetail.value = response.body()
                }
            }

            override fun onFailure(call: Call<NewsDetail>, t: Throwable) {
            }
        })
        return newsDetail
    }


}
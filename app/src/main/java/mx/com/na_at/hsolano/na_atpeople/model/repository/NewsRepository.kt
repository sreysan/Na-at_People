package mx.com.na_at.hsolano.na_atpeople.model.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import mx.com.na_at.hsolano.na_atpeople.model.News
import mx.com.na_at.hsolano.na_atpeople.model.NewsDetail
import mx.com.na_at.hsolano.na_atpeople.model.network.retrofit.ApiClient
import mx.com.na_at.hsolano.na_atpeople.util.Constants.DEVELOP_SERVER
import mx.com.na_at.hsolano.na_atpeople.util.DateUtils
import mx.com.na_at.hsolano.na_atpeople.view.contract.GetNewsCallback
import mx.com.na_at.hsolano.na_atpeople.view.contract.GetNewsDetailCallback
import mx.com.na_at.hsolano.na_atpeople.view.contract.GetNewsDetailError
import mx.com.na_at.hsolano.na_atpeople.view.contract.GetNewsError
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsRepository(context: Context) : HomeRepository(context) {

    fun requestGetAllNews(callback: GetNewsCallback) {

        val result: Call<List<News>> =
            ApiClient.buildApiService(DEVELOP_SERVER).getAllNews()

        result.enqueue(object : Callback<List<News>> {
            override fun onResponse(call: Call<List<News>>, response: Response<List<News>>) {
                if (response.isSuccessful) {
                    response.body()?.let { callback.onGetNewsSuccessful(it) }
                } else
                    callback.onGetNewsFailure(GetNewsError())
            }

            override fun onFailure(call: Call<List<News>>, t: Throwable) {
                callback.onGetNewsFailure(t)
            }
        })
    }

    fun requestNewsDetail(id: String, callback: GetNewsDetailCallback) {

        val result: Call<NewsDetail> =
            ApiClient.buildApiService(DEVELOP_SERVER).getNewsDetail(id)

        result.enqueue(object : Callback<NewsDetail> {
            override fun onResponse(
                call: Call<NewsDetail>,
                response: Response<NewsDetail>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { callback.onGetNewsDetailSuccessful(it) }
                } else
                    callback.onGetNewsDetailFailure(GetNewsDetailError())
            }

            override fun onFailure(call: Call<NewsDetail>, t: Throwable) {
                callback.onGetNewsDetailFailure(t)
            }
        })
    }


}
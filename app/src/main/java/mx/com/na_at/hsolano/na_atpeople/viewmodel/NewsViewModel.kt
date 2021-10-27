package mx.com.na_at.hsolano.na_atpeople.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mx.com.na_at.hsolano.na_atpeople.model.News
import mx.com.na_at.hsolano.na_atpeople.model.NewsDetail
import mx.com.na_at.hsolano.na_atpeople.model.repository.NewsRepository
import mx.com.na_at.hsolano.na_atpeople.util.DateUtils
import mx.com.na_at.hsolano.na_atpeople.view.contract.GetNewsCallback
import mx.com.na_at.hsolano.na_atpeople.view.contract.GetNewsDetailCallback
import mx.com.na_at.hsolano.na_atpeople.view.contract.GetNewsDetailError

class NewsViewModel(private val repository: NewsRepository) : MainViewModel(repository) {

    var news = MutableLiveData<List<News>>()
    var newsDetail = MutableLiveData<NewsDetail>()

    fun requestGetAllNews() {
        isLoading.value = true
        repository.requestGetAllNews(object : GetNewsCallback {
            override fun onGetNewsSuccessful(posts: List<News>) {
                for (post in posts) {
                    post.publishDate = DateUtils.formatUtcDate(post.publishDate)
                }
                news.postValue(posts)
                isLoading.value = false
            }

            override fun onGetNewsFailure(error: Throwable) {
                isLoading.value = false
            }
        })
    }

    fun requestGetNewsDetail(id: String) {
        if (!isConnectedToInternet())
            return

        isLoading.value = true
        repository.requestNewsDetail(id, object : GetNewsDetailCallback {
            override fun onGetNewsDetailSuccessful(detail: NewsDetail) {

                detail.publishDate =
                    DateUtils.formatDateTime(detail.publishDate)

                newsDetail.postValue(detail)
                isLoading.value = false
            }

            override fun onGetNewsDetailFailure(error: Throwable) {
                isLoading.value = false
            }
        })
    }

}
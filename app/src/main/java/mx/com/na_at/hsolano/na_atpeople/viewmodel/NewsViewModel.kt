package mx.com.na_at.hsolano.na_atpeople.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mx.com.na_at.hsolano.na_atpeople.model.News
import mx.com.na_at.hsolano.na_atpeople.model.NewsDetail
import mx.com.na_at.hsolano.na_atpeople.model.repository.NewsRepository

class NewsViewModel(private val repository: NewsRepository) : ViewModel() {

    var news = MutableLiveData<List<News>>()
    var newDetail = MutableLiveData<NewsDetail>()

    fun requestGetAllNews() {
        news = repository.requestGetAllNews()
    }

    fun requestGetNewsDetail(id: String) {
        newDetail = repository.requestNewsDetail(id)
    }

}
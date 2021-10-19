package mx.com.na_at.hsolano.na_atpeople.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mx.com.na_at.hsolano.na_atpeople.model.News
import mx.com.na_at.hsolano.na_atpeople.model.repository.NewsRepository

class NewsViewModel(private val repository: NewsRepository) : ViewModel() {

    var news = MutableLiveData<List<News>>()

    fun requestGetAllNews() {
        val news = repository.requestGetAllNotes().value
        if (news != null) {
            for (article in news) {
                article.publishDate
            }
        }

    }

}
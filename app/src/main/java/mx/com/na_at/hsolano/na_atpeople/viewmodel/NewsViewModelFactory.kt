package mx.com.na_at.hsolano.na_atpeople.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mx.com.na_at.hsolano.na_atpeople.model.repository.NewsRepository

class NewsViewModelFactory(private val repository: NewsRepository) : ViewModelProvider.Factory {
    //TODO: ADD CLASS CAST VALIDATION
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewsViewModel(repository) as T
    }
}
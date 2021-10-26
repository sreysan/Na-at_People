package mx.com.na_at.hsolano.na_atpeople.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mx.com.na_at.hsolano.na_atpeople.model.repository.RegisterActivityRepository

class HomeViewModelFactory(private val repository: RegisterActivityRepository) :
    ViewModelProvider.Factory {
    //TODO: ADD CLASS CAST VALIDATION
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(repository) as T
    }
}
package mx.com.na_at.hsolano.na_atpeople.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mx.com.na_at.hsolano.na_atpeople.model.repository.RegisterActivityRepository

class RegisterActivityModelFactory(val repository: RegisterActivityRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RegisterActivityViewModel(repository) as T
    }
}
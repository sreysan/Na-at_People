package mx.com.na_at.hsolano.na_atpeople.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mx.com.na_at.hsolano.na_atpeople.model.repository.NotificationsRepositoryImpl


class NotificationViewModelFactory(private val repository: NotificationsRepositoryImpl) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NotificationsViewModel(repository) as T
    }
}
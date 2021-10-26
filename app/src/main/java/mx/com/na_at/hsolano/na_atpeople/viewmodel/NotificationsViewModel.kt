package mx.com.na_at.hsolano.na_atpeople.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mx.com.na_at.hsolano.na_atpeople.model.database.entity.Notification
import mx.com.na_at.hsolano.na_atpeople.model.repository.NotificationsRepositoryImpl

class NotificationsViewModel(private val repository: NotificationsRepositoryImpl) : ViewModel() {
    val listModel = MutableLiveData<List<Notification>>()

    fun getAllNotifications() {
        val list = repository.getAllNotifications()
        listModel.postValue(list)
    }

    fun insertNotification(notification: Notification) {
        repository.insertNotification(notification)
    }
}
package mx.com.na_at.hsolano.na_atpeople.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mx.com.na_at.hsolano.na_atpeople.model.repository.HomeRepository

open class MainViewModel(private val repository: HomeRepository) : ViewModel() {
    var isLoading = MutableLiveData<Boolean>()
    var isConnected = MutableLiveData<Boolean>()

    fun isConnectedToInternet(): Boolean {
        val isOnline = repository.isConnectedToInternet()
        isConnected.value = isOnline
        return isOnline
    }
}
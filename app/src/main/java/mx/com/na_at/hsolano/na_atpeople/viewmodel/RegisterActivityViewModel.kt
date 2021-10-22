package mx.com.na_at.hsolano.na_atpeople.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mx.com.na_at.hsolano.na_atpeople.model.repository.RegisterActivityRepository

class RegisterActivityViewModel(private val repository: RegisterActivityRepository) : ViewModel() {

    var clients = MutableLiveData<List<Pair<String, String>>>()
    var projects = MutableLiveData<List<Pair<String, String>>>()

    fun getClients() {
        clients = repository.getAllClients()
    }

    fun getProjectsByClientId(id: String) {
        projects = repository.getProjectsByClientId(id)

    }
}
package mx.com.na_at.hsolano.na_atpeople.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mx.com.na_at.hsolano.na_atpeople.model.ActivityHour
import mx.com.na_at.hsolano.na_atpeople.model.repository.RegisterActivityRepository

class RegisterActivityViewModel(private val repository: RegisterActivityRepository) : ViewModel() {

    var clients = MutableLiveData<List<Pair<String, String>>>()
    var projects = MutableLiveData<List<Pair<String, String>>>()
    var activities = MutableLiveData<List<ActivityHour>>()

    var lessButtonState = MutableLiveData<Boolean>()
    var addButtonState = MutableLiveData<Boolean>()

    var count = MutableLiveData<Int>()

    init {
        lessButtonState.value = false
        addButtonState.value = true
        count.value = 0
    }

    fun requestGetAllClients() {
        clients = repository.getAllClients()
    }

    fun requestGetProjectsByClientId(id: String) {
        projects = repository.getProjectsByClientId(id)
    }

    fun requestGetAllActivities() {
        activities = repository.getAllActivities()
    }

    private fun updateLessButtonState() {
        lessButtonState.value = count.value!! > 0
    }


    fun addHours() {
        if (count.value!! <= 7) {
            count.value = count.value?.plus(1)
            updateLessButtonState()
        } else
            addButtonState.value = false
    }

    fun lessHour() {
        if (count.value!! > 0) {
            count.value = count.value?.minus(1)
            updateLessButtonState()
        } else
            lessButtonState.value = false

    }
}
package mx.com.na_at.hsolano.na_atpeople.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mx.com.na_at.hsolano.na_atpeople.model.ActivityHour
import mx.com.na_at.hsolano.na_atpeople.model.network.request.UpdateActivityRecordsRequest
import mx.com.na_at.hsolano.na_atpeople.model.network.response.ActivityRecordResponse
import mx.com.na_at.hsolano.na_atpeople.model.network.response.RegisterObjectResponse
import mx.com.na_at.hsolano.na_atpeople.model.repository.RegisterActivityRepository
import mx.com.na_at.hsolano.na_atpeople.view.contract.*

class RegisterActivityViewModel(private val repository: RegisterActivityRepository) : ViewModel() {

    var clients = MutableLiveData<List<Pair<String, String>>>()
    var projects = MutableLiveData<List<Pair<String, String>>>()

    var activities = MutableLiveData<List<ActivityHour>>()
    var activity = MutableLiveData<ActivityHour>()

    var activityRecords = MutableLiveData<List<ActivityRecordResponse>>()

    var hoursLiveData = MutableLiveData<Int>()
    var totalHours = 0

    var isLoading = MutableLiveData<Boolean>()
    var isConnected = MutableLiveData<Boolean>()

    var wasActivityRecordsModified = MutableLiveData<Boolean>()

    private fun isConnectedToInternet(): Boolean {
        val isOnline = repository.isConnectedToInternet()
        isConnected.value = isOnline
        return isOnline
    }

    fun requestGetAllClients() {

        if (!isConnectedToInternet())
            return

        isLoading.value = true
        repository.getAllClients(object : GetRegisterActivityListener {
            override fun onGetClientsSuccess(clientsResponse: List<RegisterObjectResponse>) {
                clients.postValue(getParListFromResponse(clientsResponse))
                isLoading.value = false
            }

            override fun onGetClientsError(error: Throwable) {
                isLoading.value = false
            }
        })
    }

    fun requestGetProjectsByClientId(id: String) {
        if (!isConnectedToInternet())
            return

        isLoading.value = true
        repository.getProjectsByClientId(id, object : GetProjectsByClientIdCallback {
            override fun onGetProjectsByClientIdSuccessful(projectsResponse: List<RegisterObjectResponse>) {
                val pairList = getParListFromResponse(projectsResponse)
                projects.postValue(pairList)
                isLoading.value = false
            }

            override fun onGetProjectsByClientIdFailure(error: Throwable) {
                isLoading.value = false

            }
        })
    }

    fun requestGetAllActivities() {
        if (!isConnectedToInternet())
            return

        isLoading.value = true
        repository.getAllActivities(object : GetActivitiesCallback {
            override fun onGetActivitiesSuccessful(activitiesResponse: List<RegisterObjectResponse>) {
                val activitiesList = mutableListOf<ActivityHour>()
                for (activity in activitiesResponse) {
                    activitiesList.add(
                        ActivityHour(
                            activity.id,
                            activity.name,
                            0,
                            false,
                            true
                        )
                    )
                    isLoading.value = false
                }
                activities.postValue(activitiesList)
            }

            override fun onGetActivitiesFailure(error: Throwable) {
                isLoading.value = false
            }
        })
    }

    fun requestGetActivityRecords() {

        if (!isConnectedToInternet())
            return

        isLoading.value = true
        repository.getActivityRecords(object : GetActivityRecordsCallback {
            override fun onGetActivityRecordsSuccessful(
                activityRecordList: List<ActivityRecordResponse>,
                daysSinceLastRecord: Int
            ) {
                activityRecords.value = activityRecordList
                isLoading.value = false
            }

            override fun onGetActivityRecordsFailure(error: Throwable) {
                isLoading.value = false
            }
        })
    }

    fun requestUpdateActivityRecords(activityRecordsId: String, duration: Int) {
        if (!isConnectedToInternet())
            return

        isLoading.value = true
        val request = UpdateActivityRecordsRequest(duration.toString())
        repository.updateActivityRecords(
            activityRecordsId,
            request,
            object : UpdateActivityRecordsCallback {
                override fun onActivityRecordUpdateSuccessful() {
                    isLoading.value = false
                    wasActivityRecordsModified.value = true
                }

                override fun onActivityRecordUpdateFailure(throwable: Throwable) {
                    isLoading.value = false
                    wasActivityRecordsModified.value = false

                }
            })
    }

    fun requestDeleteActivityRecords(activityRecordsId: String) {

        if (!isConnectedToInternet())
            return

        isLoading.value = true
        repository.deleteActivityRecords(
            activityRecordsId,
            object : DeleteActivityRecordsCallback {
                override fun onActivityRecordDeleteSuccessful() {
                    wasActivityRecordsModified.value = true
                    isLoading.value = false

                }

                override fun onActivityRecordDeleteFailure(throwable: Throwable) {
                    wasActivityRecordsModified.value = true
                    isLoading.value = false
                }
            })
    }


    private fun updateStateButtons() {
        activity.value?.hours?.let { hours ->
            activity.value?.let { currentActivity ->
                currentActivity.isLessEnable = hours > 0
            }
        }
        activity.value?.hours?.let { hours ->
            activity.value?.let { currentActivity ->
                currentActivity.isAddEnable = hours <= 7
            }
        }
        hoursLiveData.value = totalHours

    }

    fun addHours() {
        if (totalHours < 8) {
            totalHours += 1
            activity.value?.hours?.let { hours ->
                activity.value?.hours = hours.plus(1)
                updateStateButtons()
            }
        }
    }

    fun lessHours() {
        if (totalHours > 0) {
            totalHours -= 1
            activity.value?.hours?.let { hours ->
                activity.value?.hours = hours.minus(1)
                updateStateButtons()
            }
        }

    }

    private fun getParListFromResponse(values: List<RegisterObjectResponse>): List<Pair<String, String>> {
        val pairList = mutableListOf<Pair<String, String>>()
        for (item in values) {
            pairList.add(Pair(item.id, item.name))
        }
        return pairList
    }
}
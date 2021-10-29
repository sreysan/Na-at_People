package mx.com.na_at.hsolano.na_atpeople.view.contract

import mx.com.na_at.hsolano.na_atpeople.model.network.response.ActivityRecordResponse
import mx.com.na_at.hsolano.na_atpeople.model.network.response.RegisterObjectResponse

interface RegisterActivityEvents {
    fun onItemClickListener(item: Pair<String, String>)
}

interface GetRegisterActivityListener {
    fun onGetClientsSuccess(clientsResponse: List<RegisterObjectResponse>)
    fun onGetClientsError(error: Throwable)
}

interface GetProjectsByClientIdCallback {
    fun onGetProjectsByClientIdSuccessful(projectsResponse: List<RegisterObjectResponse>)
    fun onGetProjectsByClientIdFailure(error: Throwable)
}

interface GetActivitiesCallback {
    fun onGetActivitiesSuccessful(activitiesResponse: List<RegisterObjectResponse>)
    fun onGetActivitiesFailure(error: Throwable)
}

interface GetActivityRecordsCallback {
    fun onGetActivityRecordsSuccessful(
        activityRecordList: List<ActivityRecordResponse>,
        daysSinceLastRecord: Int
    )

    fun onGetActivityRecordsFailure(error: Throwable)
}

interface UpdateActivityRecordsCallback {
    fun onActivityRecordUpdateSuccessful()
    fun onActivityRecordUpdateFailure(throwable: Throwable)
}

interface DeleteActivityRecordsCallback {
    fun onActivityRecordDeleteSuccessful()
    fun onActivityRecordDeleteFailure(throwable: Throwable)
}


interface RegisterActivityRecordsCallback {
    fun onRegisterActivityRecordsSuccessful()
    fun onRegisterActivityRecordsFailure(t: Throwable)
}

class GetClientsError : Throwable()

class GetProjectsByIdError : Throwable()

class GetActivitiesError : Throwable()

class GetActivityRecordsError : Throwable()

class DeleteActivityRecordsError : Throwable()

class UpdateActivityRecordsError : Throwable()

class RegisterActivityRecordsError : Throwable()

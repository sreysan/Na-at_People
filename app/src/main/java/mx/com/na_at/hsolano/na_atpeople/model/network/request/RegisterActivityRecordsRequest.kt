package mx.com.na_at.hsolano.na_atpeople.model.network.request

data class RegisterActivityRecordsRequest(
    val idProject: String,
    val activities: List<Activity>,
    val date: String
)

data class Activity(val id: String, val duration: Int)

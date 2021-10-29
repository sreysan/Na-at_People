package mx.com.na_at.hsolano.na_atpeople.model

data class Register(
    var date: String = "",
    var activityRecords: MutableList<ActivityRecords> = mutableListOf(),
    var totalHours: Int = 0
)


data class ActivityRecords(
    val client: Client,
    val project: Project,
    var activities: MutableList<Activity> = mutableListOf()
)

data class Client(var id: String = "", var name: String = "")
data class Project(var id: String = "", var name: String = "")
data class Activity(var id: String = "", var name: String = "", var duration: Int = 0)

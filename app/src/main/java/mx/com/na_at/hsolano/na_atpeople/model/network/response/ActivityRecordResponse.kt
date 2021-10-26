package mx.com.na_at.hsolano.na_atpeople.model.network.response

data class ActivityRecordResponse(val project: Project, val activities: List<Activity>)
data class Project(val name: String, val client: Entity)
data class Activity(val activity: Entity, val duration: Int, val date: String)
data class Entity(val name: String)

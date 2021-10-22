package mx.com.na_at.hsolano.na_atpeople.model

data class ActivityHour(
    val id: String,
    val name: String,
    val hours: Int,
    var isLessEnable: Boolean,
    var isAddEnable: Boolean
)

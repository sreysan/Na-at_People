package mx.com.na_at.hsolano.na_atpeople.model

data class ActivityHour(
    val id: String,
    val name: String,
    var hours: Int,
    var isLessEnable: Boolean,
    var isAddEnable: Boolean
)

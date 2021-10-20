package mx.com.na_at.hsolano.na_atpeople.util

import java.text.SimpleDateFormat
import java.util.*

class DateUtils {
    companion object {
        fun formatUtcDate(newsDate: String): String {
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            sdf.timeZone = TimeZone.getTimeZone("UTC")
            val localTime = sdf.parse(newsDate) ?: Date()
            val newDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(localTime)

            return newDate.toString()
        }
    }
}
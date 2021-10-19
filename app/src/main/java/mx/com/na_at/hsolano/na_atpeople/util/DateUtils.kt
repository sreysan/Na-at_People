package mx.com.na_at.hsolano.na_atpeople.util

import java.text.SimpleDateFormat

class DateUtils {
    companion object {
        //TODO MODIFY FORMATTED DATE
        fun formatDate(newsDate: String): String {
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            val currentDate = sdf.parse(newsDate.substring(0, 10))
            val newDate = SimpleDateFormat("dd/MM/yyyy").format(currentDate)
            return newDate.toString()
        }
    }
}
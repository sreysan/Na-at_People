package mx.com.na_at.hsolano.na_atpeople.util

import java.text.SimpleDateFormat
import java.util.*

class DateUtils {
    companion object {
        fun formatUtcDate(newsDate: String): String {
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault())
            sdf.timeZone = TimeZone.getTimeZone("UTC")
            val localTime = sdf.parse(newsDate) ?: Date()
            val newDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(localTime)
            formatDateTime(newsDate)
            return newDate.toString()
        }

        fun formatDateTime(newsDate: String): String {
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault())
            sdf.timeZone = TimeZone.getTimeZone("UTC")
            val localTime = sdf.parse(newsDate) ?: Date()
            val newDate =
                SimpleDateFormat("dd/MM/yyyy | HH:mm", Locale.getDefault()).format(localTime)
            return newDate.toString()
        }

        fun getDateFormatted(calendar: Calendar): String {
            lateinit var dayOfWeek: String

            val dayW = calendar[Calendar.DAY_OF_WEEK]
            var month = calendar[Calendar.MONTH].toString()
            val day = calendar[Calendar.DAY_OF_MONTH]
            when (dayW) {
                1 -> dayOfWeek = "Domingo"
                2 -> dayOfWeek = "Lunes"
                3 -> dayOfWeek = "Martes"
                4 -> dayOfWeek = "Miércoles"
                5 -> dayOfWeek = "Jueves"
                6 -> dayOfWeek = "Viernes"
                7 -> dayOfWeek = "Sábado"
            }
            when (month) {
                "0" -> month = "Enero"
                "1" -> month = "Febrero"
                "2" -> month = "Marzo"
                "3" -> month = "Abril"
                "4" -> month = "Mayo"
                "5" -> month = "Junio"
                "6" -> month = "Julio"
                "7" -> month = "Agosto"
                "8" -> month = "Septiembre"
                "9" -> month = "Octubre"
                "10" -> month = "Noviembre"
                "11" -> month = "Diciembre"
            }
            return "$dayOfWeek $day de $month"
        }
    }
}
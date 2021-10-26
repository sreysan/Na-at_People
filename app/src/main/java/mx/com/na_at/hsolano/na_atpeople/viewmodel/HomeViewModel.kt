package mx.com.na_at.hsolano.na_atpeople.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mx.com.na_at.hsolano.na_atpeople.model.network.response.ActivityRecordResponse
import mx.com.na_at.hsolano.na_atpeople.model.repository.RegisterActivityRepository
import mx.com.na_at.hsolano.na_atpeople.util.DateUtils
import mx.com.na_at.hsolano.na_atpeople.view.contract.GetActivityRecordsCallback
import java.util.*

class HomeViewModel(private val repository: RegisterActivityRepository) : ViewModel() {

    val emptyDays = MutableLiveData<Boolean>()
    val days = MutableLiveData<Int>()
    val olderDay = MutableLiveData<String>()

    fun requestGetActivityRecords() {
        repository.getActivityRecords(object : GetActivityRecordsCallback {
            override fun onGetActivityRecordsSuccessful(
                activityRecords: List<ActivityRecordResponse>,
                daysSinceLastRecord: Int
            ) {
                if (daysSinceLastRecord == 0) {
                    emptyDays.postValue(true)
                } else {
                    days.postValue(daysSinceLastRecord)
                    handlePendingDates(daysSinceLastRecord)
                }
            }

            override fun onGetActivityRecordsFailure(error: Throwable) {
                emptyDays.postValue(true)
            }
        })
    }

    private fun handlePendingDates(daysPending: Int) {
        val calendars = mutableListOf<Calendar>()

        val currentDate = Date()
        val currentCalendar = Calendar.getInstance()
        currentCalendar.time = currentDate

        for (index in daysPending downTo 1) {
            val dayBefore = currentCalendar[Calendar.DAY_OF_MONTH] - index
            val calendarBefore = Calendar.getInstance()
            calendarBefore[Calendar.DAY_OF_MONTH] = dayBefore
            calendars.add(calendarBefore)
        }
        calendars.add(currentCalendar)
        olderDay.postValue(DateUtils.getDateFormatted(calendars.first()))
    }
}
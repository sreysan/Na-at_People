package mx.com.na_at.hsolano.na_atpeople.view.contract

import mx.com.na_at.hsolano.na_atpeople.model.ActivityHour

interface ActivitiesHoursEvents {
    fun onAddHourClicked(activityHour: ActivityHour, position: Int)
    fun onLessHourClicked(activityHour: ActivityHour, position: Int)
}
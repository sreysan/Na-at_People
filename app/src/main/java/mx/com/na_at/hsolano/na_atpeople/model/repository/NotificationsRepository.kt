package mx.com.na_at.hsolano.na_atpeople.model.repository

import android.content.Context
import mx.com.na_at.hsolano.na_atpeople.model.database.NotificationsDatabase
import mx.com.na_at.hsolano.na_atpeople.model.database.entity.Notification

class NotificationsRepositoryImpl(context: Context) {

    private val database = NotificationsDatabase.buildDatabase(context)

    fun getAllNotifications(): List<Notification> = database.notificationDao().getAllNotifications()

    fun insertNotification(notification: Notification) {
        database.notificationDao().insertNotification(notification)
    }
}
package mx.com.na_at.hsolano.na_atpeople.model.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import mx.com.na_at.hsolano.na_atpeople.model.database.entity.Notification


@Dao
interface NotificationDao {

    @Query("SELECT * FROM Notification")
    fun getAllNotifications(): List<Notification>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNotification(notification: Notification)
}
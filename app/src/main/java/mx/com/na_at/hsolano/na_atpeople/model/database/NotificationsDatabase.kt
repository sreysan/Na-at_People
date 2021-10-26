package mx.com.na_at.hsolano.na_atpeople.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import mx.com.na_at.hsolano.na_atpeople.model.database.dao.NotificationDao
import mx.com.na_at.hsolano.na_atpeople.model.database.entity.Notification
import mx.com.na_at.hsolano.na_atpeople.util.Constants.DATABASE_NAME

@Database(
    entities = [Notification::class],
    version = 1
)
abstract class NotificationsDatabase : RoomDatabase() {
    companion object {

        fun buildDatabase(context: Context): NotificationsDatabase {
            val database = Room.databaseBuilder(
                context,
                NotificationsDatabase::class.java,
                DATABASE_NAME
            )
            return database.allowMainThreadQueries().build()
        }

    }

    abstract fun notificationDao(): NotificationDao
}
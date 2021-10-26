package mx.com.na_at.hsolano.na_atpeople.model.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Notification(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val typeNotification: Int,
    val titleNotification: String,
    val dateNotification: String
)
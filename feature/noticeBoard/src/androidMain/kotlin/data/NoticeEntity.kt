package data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notices")
data class NoticeEntity(
    @PrimaryKey val id: String,
    val title: String,
    val details: String,
    val categoryId: Int,
    val categoryTitle: String,
    val attachmentsJson: String,
    val createdAt: Long,
    val isEmergency: Boolean,
    val latitude: Double?,
    val longitude: Double?,
    val locationText: String?,
    val isSaved: Boolean
)

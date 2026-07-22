package data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [NoticeEntity::class], version = 5, exportSchema = false)
abstract class NoticeDatabase : RoomDatabase() {
    abstract fun noticeDao(): NoticeDao
}

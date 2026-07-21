package data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NoticeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(notice: NoticeEntity)

    @Query("SELECT * FROM notices ORDER BY createdAt DESC")
    fun observeAll(): Flow<List<NoticeEntity>>
}

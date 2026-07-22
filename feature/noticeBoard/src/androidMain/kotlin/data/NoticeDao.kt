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

    @Query("UPDATE notices SET isSaved = :isSaved WHERE id = :id")
    suspend fun setSaved(id: String, isSaved: Boolean)

    @Query("SELECT * FROM notices WHERE isSaved = 1 ORDER BY createdAt DESC")
    fun observeSaved(): Flow<List<NoticeEntity>>
}

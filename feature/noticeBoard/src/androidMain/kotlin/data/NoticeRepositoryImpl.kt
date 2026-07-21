package data

import com.hasan.dnb.domain.Notice
import com.hasan.dnb.domain.NoticeAttachment
import com.hasan.dnb.notice.NoticeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class NoticeRepositoryImpl(private val dao: NoticeDao) : NoticeRepository {
    override suspend fun saveNotice(notice: Notice) {
        dao.insert(notice.toEntity())
    }

    override fun observeNotices(): Flow<List<Notice>> {
        return dao.observeAll().map { entities -> entities.map { it.toNotice() } }
    }

    private fun Notice.toEntity() = NoticeEntity(
        id = id,
        title = title,
        details = details,
        categoryId = categoryId,
        categoryTitle = categoryTitle,
        attachmentsJson = Json.encodeToString<List<NoticeAttachment>>(attachments),
        createdAt = createdAt,
        isEmergency = isEmergency,
        latitude = latitude,
        longitude = longitude
    )

    private fun NoticeEntity.toNotice() = Notice(
        id = id,
        title = title,
        details = details,
        categoryId = categoryId,
        categoryTitle = categoryTitle,
        attachments = Json.decodeFromString<List<NoticeAttachment>>(attachmentsJson),
        createdAt = createdAt,
        isEmergency = isEmergency,
        latitude = latitude,
        longitude = longitude
    )
}

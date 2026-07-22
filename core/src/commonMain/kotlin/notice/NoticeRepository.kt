package com.hasan.dnb.notice

import com.hasan.dnb.domain.Notice
import kotlinx.coroutines.flow.Flow

interface NoticeRepository {
    suspend fun saveNotice(notice: Notice)
    fun observeNotices(): Flow<List<Notice>>
    suspend fun setSaved(id: String, saved: Boolean)
    fun observeSavedNotices(): Flow<List<Notice>>
}

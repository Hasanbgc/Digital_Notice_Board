package com.hasan.dnb.domain

import kotlinx.serialization.Serializable

@Serializable
data class Notice(
    val id: String,
    val title: String,
    val details: String,
    val categoryId: Int,
    val categoryTitle: String,
    val attachments: List<NoticeAttachment>,
    val createdAt: Long,
    val isEmergency: Boolean = false,
    val latitude: Double? = null,
    val longitude: Double? = null
)

@Serializable
data class NoticeAttachment(
    val id: String,
    val name: String,
    val type: String,
    val uri: String?
)

package domain.model

import kotlinx.serialization.Serializable

@Serializable
data class PostResponse(
    val id: String,
    val userId: String,
    val communityId: String,
    val postTypeId: Int,
    val categoryId: Int,
    val statusId: Int,
    val visibilityId: Int,
    val title: String,
    val description: String? = null,
    val priority: Int? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val locationText: String? = null,
    val radiusKm: Double? = null,
    val expiresAt: String? = null
)

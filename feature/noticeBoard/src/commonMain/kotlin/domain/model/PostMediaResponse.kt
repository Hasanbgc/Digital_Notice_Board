package domain.model

import kotlinx.serialization.Serializable

@Serializable
data class PostMediaResponse(
    val id: String,
    val fileUrl: String,
    val mediaType: String,
    val fileName: String? = null
)

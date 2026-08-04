package domain.model

import kotlinx.serialization.Serializable

@Serializable
sealed class AttachmentItem {
    abstract val id: String
    abstract val url: String

    @Serializable
    data class Image(
        override val id: String,
        override val url: String
    ) : AttachmentItem()

    @Serializable
    data class Video(
        override val id: String,
        override val url: String,
        val thumbnailUrl: String? = null
    ) : AttachmentItem()

    @Serializable
    data class Pdf(
        override val id: String,
        override val url: String,
        val fileName: String
    ) : AttachmentItem()

    @Serializable
    data class Unknown(
        override val id: String,
        override val url: String,
        val fileName: String
    ) : AttachmentItem()
}

/**
 * Extension to convert PostMediaResponse into strongly typed AttachmentItem.
 */
fun PostMediaResponse.toAttachmentItem(): AttachmentItem {
    val cleanUrl = fileUrl.trim()
    val lowerUrl = cleanUrl.lowercase()
    val mediaTypeUpper = mediaType.uppercase()
    val name = fileName ?: cleanUrl.substringAfterLast('/').ifBlank { "Attachment" }

    return when {
        mediaTypeUpper == "IMAGE" || lowerUrl.endsWith(".png") || lowerUrl.endsWith(".jpg") ||
                lowerUrl.endsWith(".jpeg") || lowerUrl.endsWith(".webp") || lowerUrl.endsWith(".gif") -> {
            AttachmentItem.Image(id = id, url = cleanUrl)
        }

        mediaTypeUpper == "VIDEO" || lowerUrl.endsWith(".mp4") || lowerUrl.endsWith(".mkv") ||
                lowerUrl.endsWith(".mov") || lowerUrl.endsWith(".avi") || lowerUrl.endsWith(".webm") -> {
            AttachmentItem.Video(id = id, url = cleanUrl)
        }

        mediaTypeUpper == "PDF" || lowerUrl.endsWith(".pdf") -> {
            AttachmentItem.Pdf(id = id, url = cleanUrl, fileName = name)
        }

        else -> {
            AttachmentItem.Unknown(id = id, url = cleanUrl, fileName = name)
        }
    }
}

/**
 * Extension to convert plain string URLs/file paths into AttachmentItem.
 */
fun String.toAttachmentItem(index: Int = 0): AttachmentItem {
    val cleanUrl = this.trim()
    val lowerUrl = cleanUrl.lowercase()
    val name = cleanUrl.substringAfterLast('/').ifBlank { "Attachment #${index + 1}" }

    return when {
        lowerUrl.endsWith(".png") || lowerUrl.endsWith(".jpg") || lowerUrl.endsWith(".jpeg") ||
                lowerUrl.endsWith(".webp") || lowerUrl.endsWith(".gif") -> {
            AttachmentItem.Image(id = "img_$index", url = cleanUrl)
        }

        lowerUrl.endsWith(".mp4") || lowerUrl.endsWith(".mkv") || lowerUrl.endsWith(".mov") ||
                lowerUrl.endsWith(".avi") || lowerUrl.endsWith(".webm") -> {
            AttachmentItem.Video(id = "vid_$index", url = cleanUrl)
        }

        lowerUrl.endsWith(".pdf") -> {
            AttachmentItem.Pdf(id = "pdf_$index", url = cleanUrl, fileName = name)
        }

        else -> {
            AttachmentItem.Unknown(id = "unk_$index", url = cleanUrl, fileName = name)
        }
    }
}

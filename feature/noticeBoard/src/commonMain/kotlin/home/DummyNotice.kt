package home

import androidx.compose.runtime.remember
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.core.scope.Scope
import kotlin.collections.emptyList
import kotlin.coroutines.coroutineContext

fun generateDummyNotices(page: Int, pageSize: Int): List<Poster.Normal> {
    val start = (page - 1) * pageSize

    return List(pageSize) { index ->
        val id = start + index
            Poster.Normal(
                id = id,
                title = "University Admission Test Result Published",
                description = "Results for the 2024 admission test are now available. Students can check their results using their registration number and celebrate their achievements!," +
                        "Results for the 2024 admission test are now available. Students can check their results using their registration number and celebrate their achievements!"+
                        "Results for the 2024 admission test are now available. Students can check their results using their registration number and celebrate their achievements!",
                date = "5/11/2025",
                distance = "2km away",
                time = "5 min ago",
                imageUrlList = listOf(
                    "https://picsum.photos/id/${id + 10}/400/400",
                    "https://picsum.photos/id/${id + 20}/300/300",
                    "https://picsum.photos/id/${id + 30}/300/300",
                ),
                location = "Dhanmondi Area",
                type = Type.URGENT,
                profile = Profile(
                    name = "John Doe",
                    imageUrl = "https://picsum.photos/id/10/400/300",
                    institution = "Chittagong University",
                    designation = "Student"
                ),
                attachments = listOf("Job_description.pdf", "Application_Form.docx"),
                isFavorite = false,
                shareCount = 23,
                commentCount = 45,
                likeCount = 123,
                isSaved = savedList.any { it.id == id },
                viewCount = 1250,
                isExpanded = false,
                liked = Like.IDLE
            )
    }
}

val savedList = mutableListOf<Poster.Normal>()
private var onSavedListChanged: (() -> Unit)? = null

fun setOnSavedListChangedListener(listener: () -> Unit) {
    onSavedListChanged = listener
}

fun addSavedNote(poster: Poster.Normal) {
    if (savedList.none { it.id == poster.id }) {
        savedList.add(poster.copy(isSaved = true))
        onSavedListChanged?.invoke()
    }
}

fun getSavedNotes(): List<Poster.Normal> {
    return savedList.toList()
}

fun findPosterById(id: Int): Poster.Normal? {
    // We generate a large enough batch to find the ID since it's dummy data
    return generateDummyNotices(1, 500).find { it.id == id }
}

